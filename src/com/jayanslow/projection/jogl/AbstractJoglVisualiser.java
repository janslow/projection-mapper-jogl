package com.jayanslow.projection.jogl;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.painter.PainterFactory;
import com.jayanslow.projection.texture.controllers.TextureController;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.models.Universe;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.GLReadBufferUtil;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public abstract class AbstractJoglVisualiser extends VisualiserFrame implements GLEventListener {
	private static final long	serialVersionUID	= 1864514209659235403L;

	private GLU					glu;
	private final GLJPanel		canvas;
	private final FPSAnimator	animator;

	public AbstractJoglVisualiser(WorldController world, String title, int height, int width, PainterFactory f) {
		super(world, f, title);

		final GLProfile glp = GLProfile.getDefault();
		final GLCapabilities caps = new GLCapabilities(glp);
		canvas = new GLJPanel(caps);

		add(canvas);
		canvas.addGLEventListener(this);
		canvas.setPreferredSize(new Dimension(width, height));

		animator = new FPSAnimator(canvas, 60);
		animator.start();
		animator.setUpdateFPSFrames(3, null);

		pack();
	}

	public AbstractJoglVisualiser(WorldController world, TextureController textures, String title, int height, int width) {
		this(world, title, height, width, VisualiserFrame.setUpPainterFactory(textures));
		textures.addTextureListener(this);
	}

	@Override
	public synchronized void addKeyListener(KeyListener l) {
		super.addKeyListener(l);
		canvas.addKeyListener(l);
	}

	@Override
	public synchronized void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		canvas.addMouseListener(l);
	}

	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		canvas.addMouseMotionListener(l);
	}

	@Override
	public synchronized void addMouseWheelListener(MouseWheelListener l) {
		super.addMouseWheelListener(l);
		canvas.addMouseWheelListener(l);
	}

	@Override
	public void cameraChangeResolution(Camera camera, int oldHeight, int oldWidth) {
		super.cameraChangeResolution(camera, oldHeight, oldWidth);

		int height = camera.getResolutionHeight(), width = camera.getResolutionWidth();
		setSize(getWidth() - oldWidth + width, getHeight() - oldHeight + height);
		canvas.setPreferredSize(new Dimension(width, height));
		pack();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context

		if (isMarkedDirty()) {
			reshape(gl, getCamera());
			markClean();
		}
		update();
		render(gl);
		if (saveNextFrame) {
			save(gl, outputFile);
			saveNextFrame = false;
		}
		markClean();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public float getFPS() {
		return animator.getLastFPS();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities
		gl.glClearColor(0, 0, 0, 0); // set background (clear) color
		gl.glClearDepth(1); // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing

		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

		gl.glDepthFunc(GL.GL_LEQUAL); // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	}

	@Override
	protected synchronized void markClean() {
		super.markClean();
		animator.pause();
	}

	@Override
	protected synchronized void markDirty() {
		super.markDirty();
		animator.resume();
	}

	private void render(GL2 gl) {
		Camera camera = getCamera();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		gl.glLoadIdentity(); // reset the model-view matrix

		gl.glPushMatrix();
		Vector3f eye = camera.getPosition();
		Vector3f center = camera.getCentre(10);
		Vector3f up = camera.getUp();
		glu.gluLookAt(eye.x, eye.y, -eye.z, center.x, center.y, -center.z, up.x, up.y, -up.z);

		gl.glPushMatrix();
		gl.glScalef(1, 1, -1);

		getPainterFactory().paint(gl, Universe.class, getWorldController().getUniverse(), getRenderMode());
		getPainterFactory().paint(gl, Origin.class, new Origin(camera.getPosition().length() / 10), getRenderMode());
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	private void reshape(GL2 gl, Camera camera) {
		reshape(gl, camera.getResolutionHeight(), camera.getResolutionWidth());
	}

	private void reshape(GL2 gl, int height, int width) {
		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(Math.toDegrees(getCamera().getFieldOfView()), aspect, 1, 100000.0); // fovy, aspect, zNear,
																								// zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		reshape(gl, height, width);
	}

	private void save(GL2 gl, File file) {
		GLReadBufferUtil u = new GLReadBufferUtil(true, false);
		u.readPixels(gl, false);
		TextureData t = u.getTextureData();
		try {
			file.createNewFile();
			TextureIO.write(t, file);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void saveFrame(File outputFile) {
		this.outputFile = outputFile;
		saveNextFrame = true;
	}

	protected abstract boolean update();
}

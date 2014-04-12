package com.jayanslow.projection.jogl;

import java.awt.Frame;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.painter.MapPainterFactory;
import com.jayanslow.projection.jogl.painter.OriginPainter;
import com.jayanslow.projection.jogl.painter.Painter;
import com.jayanslow.projection.jogl.painter.PainterFactory;
import com.jayanslow.projection.jogl.painter.UniversePainter;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.models.Universe;
import com.jogamp.opengl.util.FPSAnimator;

public abstract class AbstractVisualiser extends Frame implements GLEventListener {
	private static final long	serialVersionUID	= 1864514209659235403L;

	private static PainterFactory setUpPainterFactory() {
		PainterFactory f = new MapPainterFactory(new HashMap<Class<?>, Painter<?>>());

		UniversePainter.registerNested(f);
		OriginPainter.registerNested(f);

		return f;
	}

	private final WorldController	world;
	private final PainterFactory	f;
	private GLU						glu;

	private final GLCanvas			canvas;
	private final FPSAnimator		animator;

	public AbstractVisualiser(WorldController world, String title) {
		this(world, title, setUpPainterFactory());
	}

	public AbstractVisualiser(WorldController world, String title, PainterFactory f) {
		super(title);
		this.world = world;
		this.f = f;

		final GLProfile glp = GLProfile.getDefault();
		final GLCapabilities caps = new GLCapabilities(glp);
		canvas = new GLCanvas(caps);

		add(canvas);
		canvas.addGLEventListener(this);

		animator = new FPSAnimator(canvas, 60);
		animator.start();
		animator.setUpdateFPSFrames(3, null);

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
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		update();
		render(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	public abstract Camera getCamera();

	public float getFPS() {
		return animator.getLastFPS();
	}

	public PainterFactory getPainterFactory() {
		return f;
	}

	protected abstract RenderMode getRenderMode();

	public WorldController getWorld() {
		return world;
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

		f.paint(gl, Universe.class, world.getUniverse(), getRenderMode());
		f.paint(gl, Origin.class, new Origin(camera.getPosition().length() / 10), getRenderMode());
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0, aspect, 1, 100000.0); // fovy, aspect, zNear, zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	protected abstract boolean update();
}
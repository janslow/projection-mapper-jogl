package com.jayanslow.projection.jogl;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import com.jayanslow.projection.world.models.Rotation3f;
import com.jayanslow.projection.world.models.Universe;
import com.jogamp.opengl.util.FPSAnimator;

public class Visualiser extends Frame implements GLEventListener {
	private static final long	serialVersionUID	= -97103914923218634L;

	private static void setCamera(GLU glu, Camera camera) {
		Vector3f eye = camera.getPosition();
		Vector3f center = camera.getCentre(10);
		Vector3f up = camera.getUp();
		glu.gluLookAt(eye.x, eye.y, -eye.z, center.x, center.y, -center.z, up.x, up.y, -up.z);
	}

	private final Universe			universe;

	private final PainterFactory	f;

	private GLU						glu;

	private final Camera			camera;

	private final CameraController	cameraController;

	private RenderMode				renderMode;

	public Visualiser(Universe universe) {
		super();
		this.universe = universe;

		f = new MapPainterFactory(new HashMap<Class<?>, Painter<?>>());
		UniversePainter.registerNested(f);
		OriginPainter.registerNested(f);

		final GLProfile glp = GLProfile.getDefault();
		final GLCapabilities caps = new GLCapabilities(glp);
		final GLCanvas canvas = new GLCanvas(caps);

		add(canvas);
		canvas.addGLEventListener(this);

		final FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.start();

		// Set Up Camera
		Vector3f dimensions = universe.getDimensions();
		Vector3f startPosition = new Vector3f();
		startPosition.x = dimensions.x * 0.5f;
		startPosition.y = dimensions.y * 1.2f;
		startPosition.z = -Math.max(dimensions.x, dimensions.y) * 1.5f;
		Rotation3f startRotation = new Rotation3f(0, 0, 0);
		startRotation.x = (float) Math.tan(startPosition.y / (dimensions.z * 1.5f - startPosition.z));
		camera = new Camera(startPosition, startRotation);

		// Set Up CameraController
		cameraController = new CameraController(camera, 100);
		canvas.addKeyListener(cameraController);
		canvas.addMouseWheelListener(cameraController);
		canvas.addMouseMotionListener(cameraController);
		addKeyListener(cameraController);
		addMouseWheelListener(cameraController);
		addMouseMotionListener(cameraController);

		// Set Up RenderModeController
		renderMode = RenderMode.WIREFRAME;
		KeyAdapter renderModeController = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				setRenderMode(e.getKeyCode());
			}

			public void setRenderMode(int keyCode) {
				switch (keyCode) {
				case KeyEvent.VK_1:
					renderMode = RenderMode.WIREFRAME;
					break;
				case KeyEvent.VK_2:
					renderMode = RenderMode.OPAQUE_WIREFRAME;
					break;
				case KeyEvent.VK_3:
					renderMode = RenderMode.SOLID;
					break;
				}
			}
		};
		canvas.addKeyListener(renderModeController);
		addKeyListener(renderModeController);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		update();
		render(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities
		gl.glClearColor(0, 0, 0, 0); // set background (clear) color
		gl.glClearDepth(1); // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL.GL_LEQUAL); // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	}

	private void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		gl.glLoadIdentity(); // reset the model-view matrix

		gl.glPushMatrix();
		setCamera(glu, camera);

		gl.glPushMatrix();
		gl.glScalef(1, 1, -1);

		f.paint(gl, Universe.class, universe, renderMode);
		f.paint(gl, Origin.class, new Origin(camera.getPosition().length() / 10), renderMode);
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

	private void update() {
		cameraController.step();
	}
}

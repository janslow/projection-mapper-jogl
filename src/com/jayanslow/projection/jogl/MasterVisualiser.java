package com.jayanslow.projection.jogl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.models.Rotation3f;

public class MasterVisualiser extends AbstractVisualiser {
	private static final long	serialVersionUID	= -97103914923218634L;

	private static StandaloneCamera setUpCamera(Vector3f dimensions) {
		// Set Up Camera
		Vector3f startPosition = new Vector3f();
		startPosition.x = dimensions.x * 0.5f;
		startPosition.y = dimensions.y * 1.2f;
		startPosition.z = -Math.max(dimensions.x, dimensions.y) * 1.5f;
		Rotation3f startRotation = new Rotation3f(0, 0, 0);
		startRotation.x = (float) Math.tan(startPosition.y / (dimensions.z * 1.5f - startPosition.z));
		return new StandaloneCamera(startPosition, startRotation);
	}

	final CameraController			cameraController;
	private final StandaloneCamera	camera;
	private RenderMode				renderMode;

	public MasterVisualiser(WorldController world) {
		this(world, RenderMode.OPAQUE_WIREFRAME);
	}

	public MasterVisualiser(WorldController world, RenderMode renderMode) {
		super(world, "Universe");

		camera = setUpCamera(world.getUniverse().getDimensions());
		this.renderMode = renderMode;

		// Set Up CameraController
		cameraController = new CameraController(getCamera(), 100);
		addKeyListener(cameraController);
		addMouseWheelListener(cameraController);
		addMouseMotionListener(cameraController);

		KeyAdapter renderModeController = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				setRenderMode(e.getKeyCode());
			}

			public void setRenderMode(int keyCode) {
				switch (keyCode) {
				case KeyEvent.VK_1:
					MasterVisualiser.this.setRenderMode(RenderMode.WIREFRAME);
					break;
				case KeyEvent.VK_2:
					MasterVisualiser.this.setRenderMode(RenderMode.OPAQUE_WIREFRAME);
					break;
				case KeyEvent.VK_3:
					MasterVisualiser.this.setRenderMode(RenderMode.SOLID);
					break;
				}
			}
		};
		addKeyListener(renderModeController);
	}

	@Override
	public StandaloneCamera getCamera() {
		return camera;
	}

	@Override
	public RenderMode getRenderMode() {
		return renderMode;
	}

	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}

	@Override
	protected boolean update() {
		return cameraController.step();
	}
}

package com.jayanslow.projection.jogl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.texture.controllers.TextureController;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.models.Rotation3f;

public class MasterJoglVisualiser extends AbstractJoglVisualiser {
	private static final long	serialVersionUID	= -97103914923218634L;

	private static StandaloneCamera setUpCamera(Vector3f dimensions, int resolutionHeight, int resolutionWidth) {
		// Set Up Camera
		Vector3f startPosition = new Vector3f();
		startPosition.x = dimensions.x * 0.5f;
		startPosition.y = dimensions.y * 1.2f;
		startPosition.z = -Math.max(dimensions.x, dimensions.y) * 1.5f;
		Rotation3f startRotation = new Rotation3f(0, 0, 0);
		startRotation.x = (float) Math.tan(startPosition.y / (dimensions.z * 1.5f - startPosition.z));
		return new StandaloneCamera(startPosition, startRotation, resolutionHeight, resolutionWidth);
	}

	final CameraController			cameraController;
	private final StandaloneCamera	camera;
	private RenderMode				renderMode;

	public MasterJoglVisualiser(WorldController world, TextureController textures, int height, int width) {
		this(world, textures, height, width, RenderMode.OUTLINE);
	}

	public MasterJoglVisualiser(WorldController world, TextureController textures, int height, int width,
			RenderMode renderMode) {
		super(world, textures, "Universe", height, width);

		camera = setUpCamera(world.getUniverse().getDimensions(), height, width);
		camera.addCameraListener(this);

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
				markDirty();
			}

			public void setRenderMode(int keyCode) {
				switch (keyCode) {
				case KeyEvent.VK_1:
					MasterJoglVisualiser.this.setRenderMode(RenderMode.WIREFRAME);
					break;
				case KeyEvent.VK_2:
					MasterJoglVisualiser.this.setRenderMode(RenderMode.OUTLINE);
					break;
				case KeyEvent.VK_3:
					MasterJoglVisualiser.this.setRenderMode(RenderMode.SOLID);
					break;
				case KeyEvent.VK_4:
					MasterJoglVisualiser.this.setRenderMode(RenderMode.TEXTURED);
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
		if (cameraController.isChanging())
			return RenderMode.OUTLINE;
		return renderMode;
	}

	@Override
	protected synchronized void markClean() {
		if (!cameraController.isChanging())
			super.markClean();
	}

	public void setRenderMode(RenderMode renderMode) throws NullPointerException {
		if (renderMode == null)
			throw new NullPointerException();
		markDirty();
		this.renderMode = renderMode;
	}

	@Override
	protected boolean update() {
		return cameraController.step();
	}

}

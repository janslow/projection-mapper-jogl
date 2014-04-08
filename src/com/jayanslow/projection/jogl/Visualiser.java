package com.jayanslow.projection.jogl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.jayanslow.projection.world.models.Universe;

public class Visualiser extends AbstractVisualiser {
	private static final long	serialVersionUID	= -97103914923218634L;

	final CameraController		cameraController;

	public Visualiser(Universe universe) {
		super(universe, "");

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
					Visualiser.this.setRenderMode(RenderMode.WIREFRAME);
					break;
				case KeyEvent.VK_2:
					Visualiser.this.setRenderMode(RenderMode.OPAQUE_WIREFRAME);
					break;
				case KeyEvent.VK_3:
					Visualiser.this.setRenderMode(RenderMode.SOLID);
					break;
				}
			}
		};
		addKeyListener(renderModeController);
	}

	@Override
	protected void update() {
		cameraController.step();
		System.out.println(getFPS());
	}
}

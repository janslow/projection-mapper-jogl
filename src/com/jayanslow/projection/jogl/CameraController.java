package com.jayanslow.projection.jogl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.vecmath.Vector3f;

public class CameraController implements KeyListener, MouseWheelListener, MouseMotionListener {
	private static final float		PAN_SPEED	= 0.01f, TILT_SPEED = 0.01f, FORWARD_SPEED = 1, SIDE_SPEED = 1,
			FLY_SPEED = 1, SCROLL_SPEED = 1;

	private boolean					isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed,
			isShiftPressed;

	private float					scroll;

	private int						lastX		= -1;

	private int						lastY		= -1;

	private final StandaloneCamera	camera;

	private final float				speedScale;

	private boolean					isAltOrMetaPressed;

	public CameraController(StandaloneCamera camera, float speedScale) {
		this.camera = camera;
		this.speedScale = speedScale;
	}

	public boolean isChanging() {
		return isMoving() || isRotating();
	}

	public boolean isMoving() {
		return isLeftPressed || isRightPressed || isUpPressed || isDownPressed || isSpacePressed || isShiftPressed;
	}

	private boolean isRotating() {
		return isAltOrMetaPressed;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_H:
			camera.goToHome();
			break;
		case KeyEvent.VK_O:
			camera.lookAtOrigin();
			break;
		case KeyEvent.VK_L:
			System.out.printf("Position: %s Rotation: %s Centre: %s\n", camera.getPosition(), camera.getRotation(),
					camera.getCentre(10));
			break;
		default:
			updateKeys(key, true);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		updateKeys(key, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getXOnScreen();
		int y = e.getYOnScreen();

		float diffX = 0, diffY = 0;
		if (lastX >= 0)
			diffX = lastX - x;
		lastX = x;
		if (lastY >= 0)
			diffY = lastY - y;
		lastY = y;

		if (e.isAltDown() || e.isMetaDown()) {
			camera.pan(diffX * PAN_SPEED);
			camera.tilt(diffY * TILT_SPEED);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = (float) e.getPreciseWheelRotation();
	}

	public boolean step() {
		float forwardSpeed = isUpPressed ? FORWARD_SPEED : (isDownPressed ? -FORWARD_SPEED : scroll * SCROLL_SPEED);
		float sideSpeed = isRightPressed ? SIDE_SPEED : (isLeftPressed ? -SIDE_SPEED : 0);
		scroll = 0;
		camera.moveRelative(new Vector3f(speedScale * sideSpeed, 0, speedScale * forwardSpeed));

		float flySpeed = isSpacePressed ? FLY_SPEED : (isShiftPressed ? -FLY_SPEED : 0);
		camera.moveAbsolute(new Vector3f(0, speedScale * flySpeed, 0));

		return forwardSpeed != 0 || sideSpeed != 0 || flySpeed != 0;
	}

	private void updateKeys(int keyCode, boolean pressed) {
		switch (keyCode) {
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			isLeftPressed = pressed;
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			isRightPressed = pressed;
			break;
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			isUpPressed = pressed;
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			isDownPressed = pressed;
			break;
		case KeyEvent.VK_SPACE:
			isSpacePressed = pressed;
			break;
		case KeyEvent.VK_SHIFT:
			isShiftPressed = pressed;
			break;
		case KeyEvent.VK_META:
		case KeyEvent.VK_ALT:
			isAltOrMetaPressed = pressed;
			if (!pressed)
				isLeftPressed = isRightPressed = isUpPressed = isDownPressed = isSpacePressed = isShiftPressed = false;
			break;
		}
	}
}

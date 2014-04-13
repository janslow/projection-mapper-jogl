package com.jayanslow.projection.jogl;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.models.Rotation3f;

public class StandaloneCamera extends AbstractCamera {
	private int					resolutionHeight;
	private int					resolutionWidth;
	private final Vector3f		homePosition;
	private final Rotation3f	homeRotation;
	private final Vector3f		position;
	private final Rotation3f	rotation;

	public StandaloneCamera(Vector3f position, Rotation3f rotation, int resolutionHeight, int resolutionWidth) {
		homePosition = new Vector3f(position);
		homeRotation = new Rotation3f(rotation);
		this.position = new Vector3f(position);
		this.rotation = new Rotation3f(rotation);
		this.resolutionHeight = resolutionHeight;
		this.resolutionWidth = resolutionWidth;
	}

	@Override
	public float getFieldOfView() {
		return (float) (Math.PI / 4);
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	@Override
	public int getResolutionHeight() {
		return resolutionHeight;
	}

	@Override
	public int getResolutionWidth() {
		return resolutionWidth;
	}

	@Override
	public Rotation3f getRotation() {
		return new Rotation3f(rotation);
	}

	public void goToHome() {
		setPosition(homePosition);
		setRotation(homeRotation);
	}

	public void lookAt(Vector3f v) {
		v = new Vector3f(v);
		v.sub(position);
		v.normalize();

		if (v.length() == 0)
			return;

		float thetaX = (float) Math.atan(v.y / v.z);
		Matrix3f mX = new Matrix3f();
		mX.rotX(thetaX);
		mX.transform(v);

		float thetaY = (float) Math.atan(-v.x / v.z);
		Matrix3f mY = new Matrix3f();
		mY.rotX(thetaY);
		mY.transform(v);
		if (v.z < 0)
			thetaY += Math.PI;

		setRotation(new Rotation3f(-thetaX, -thetaY, 0));
	}

	public void lookAtOrigin() {
		lookAt(new Vector3f(0, 0, 0));
	}

	public void moveAbsolute(Vector3f v) {
		Vector3f temp = new Vector3f(position);
		temp.add(v);
		setPosition(temp);
	}

	public void moveRelative(Vector3f movementVector) {
		movementVector = new Vector3f(movementVector);
		rotation.rotate(movementVector);
		moveAbsolute(movementVector);
	}

	public void pan(float dPan) {
		Rotation3f temp = new Rotation3f(rotation);
		temp.y += dPan;
		setRotation(temp);
	}

	public void roll(float dRoll) {
		Rotation3f temp = new Rotation3f(rotation);
		temp.z += dRoll;
		setRotation(temp);
	}

	public void setPosition(Vector3f position) {
		if (position == null)
			throw new NullPointerException();

		Vector3f old = new Vector3f(this.position);

		this.position.set(position);

		fireCameraMove(old);
	}

	public void setResolution(int height, int width) throws IllegalArgumentException {
		if (height < 1)
			throw new IllegalArgumentException("Height must be a positive integer");
		if (width < 1)
			throw new IllegalArgumentException("Width must be a positive integer");

		int oldHeight = resolutionHeight, oldWidth = resolutionWidth;

		resolutionHeight = height;
		resolutionWidth = width;

		fireCameraChangeResolution(oldHeight, oldWidth);
	}

	public void setRotation(Rotation3f rotation) {
		if (rotation == null)
			throw new NullPointerException();

		Rotation3f old = new Rotation3f(rotation);

		rotation.set(rotation);

		fireCameraRotate(old);
	}

	public void tilt(float dTilt) {
		Rotation3f temp = new Rotation3f(rotation);
		temp.x += dTilt;
		setRotation(temp);
	}
}

package com.jayanslow.projection.jogl;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.utils.VectorOperations;
import com.jayanslow.projection.world.models.Rotation3f;

public class Camera {
	private final Vector3f		homePosition;
	private final Rotation3f	homeRotation;
	private final Vector3f		position;
	private final Rotation3f	rotation;

	public Camera(Vector3f position, Rotation3f rotation) {
		homePosition = new Vector3f(position);
		homeRotation = new Rotation3f(rotation);
		this.position = new Vector3f(position);
		this.rotation = new Rotation3f(rotation);
	}

	public Vector3f getCentre(float distance) {
		Vector3f center = new Vector3f(0, 0, 1);
		rotation.rotate(center);
		center.scale(distance);
		center.add(position);
		return center;
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public Rotation3f getRotation() {
		return new Rotation3f(rotation);
	}

	public Vector3f getUp() {
		return VectorOperations.rotate(new Vector3f(0, 1, 0), rotation);
	}

	public void home() {
		position.set(homePosition);
		rotation.set(homeRotation);
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

		rotation.set(-thetaX, -thetaY, 0);
	}

	public void moveAbsolute(Vector3f v) {
		position.add(v);
	}

	public void moveRelative(Vector3f movementVector) {
		movementVector = new Vector3f(movementVector);
		rotation.rotate(movementVector);
		moveAbsolute(movementVector);
	}

	public void origin() {
		lookAt(new Vector3f(0, 0, 0));
	}

	public void pan(float dPan) {
		rotation.y += dPan;
	}

	public void roll(float dRoll) {
		rotation.z += dRoll;
	}

	public void setPosition(Vector3f position) {
		this.position.set(position);
	}

	public void setRotation(Rotation3f direction) {
		rotation.set(direction);
	}

	public void tilt(float dTilt) {
		rotation.x += dTilt;
	}
}

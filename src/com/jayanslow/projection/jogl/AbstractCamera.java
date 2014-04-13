package com.jayanslow.projection.jogl;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.utils.VectorOperations;
import com.jayanslow.projection.world.models.Rotation3f;

public abstract class AbstractCamera implements Camera {
	private final List<CameraListener>	cameraListeners	= new LinkedList<CameraListener>();

	public AbstractCamera() {
		super();
	}

	@Override
	public void addCameraListener(CameraListener l) {
		if (l == null)
			return;
		cameraListeners.add(l);
	}

	protected void fireCameraChangeFieldOfView(float old) {
		for (CameraListener l : cameraListeners)
			l.cameraChangeFieldOfView(this, old);
	}

	protected void fireCameraChangeResolution(int oldHeight, int oldWidth) {
		for (CameraListener l : cameraListeners)
			l.cameraChangeResolution(this, oldHeight, oldWidth);
	}

	protected void fireCameraMove(Vector3f old) {
		for (CameraListener l : cameraListeners)
			l.cameraMove(this, old);
	}

	protected void fireCameraRotate(Rotation3f old) {
		for (CameraListener l : cameraListeners)
			l.cameraRotate(this, old);
	}

	@Override
	public float getAspectRatio() {
		float height = getResolutionHeight();
		if (height <= 0)
			height = 1;
		return getResolutionWidth() / height;
	}

	@Override
	public Vector3f getCentre(float distance) {
		Vector3f center = new Vector3f(0, 0, 1);
		getRotation().rotate(center);
		center.scale(distance);
		center.add(getPosition());
		return center;
	}

	protected abstract Rotation3f getRotation();

	@Override
	public Vector3f getUp() {
		return VectorOperations.rotate(new Vector3f(0, 1, 0), getRotation());
	}

}

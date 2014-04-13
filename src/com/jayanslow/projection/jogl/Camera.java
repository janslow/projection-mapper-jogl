package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

public interface Camera {

	public void addCameraListener(CameraListener l);

	public float getAspectRatio();

	public Vector3f getCentre(float distance);

	public float getFieldOfView();

	public Vector3f getPosition();

	public int getResolutionHeight();

	public int getResolutionWidth();

	public Vector3f getUp();
}

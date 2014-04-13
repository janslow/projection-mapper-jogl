package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.models.Rotation3f;

public interface CameraListener {
	public void cameraChangeFieldOfView(Camera camera, float old);

	public void cameraChangeResolution(Camera camera, int oldHeight, int oldWidth);

	public void cameraMove(Camera camera, Vector3f old);

	public void cameraRotate(Camera camera, Rotation3f old);
}

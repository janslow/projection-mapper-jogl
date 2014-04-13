package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

public interface Camera {

	public Vector3f getCentre(float distance);

	public Vector3f getPosition();

	public Vector3f getUp();

}

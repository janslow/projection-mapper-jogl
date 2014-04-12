package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

public interface Camera {

	public abstract Vector3f getCentre(float distance);

	public abstract Vector3f getPosition();

	public abstract Vector3f getUp();

}

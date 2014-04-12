package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.utils.VectorOperations;
import com.jayanslow.projection.world.models.Rotation3f;

public abstract class AbstractCamera implements Camera {

	public AbstractCamera() {
		super();
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

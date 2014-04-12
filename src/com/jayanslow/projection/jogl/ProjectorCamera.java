package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.models.Projector;
import com.jayanslow.projection.world.models.Rotation3f;

public class ProjectorCamera extends AbstractCamera {

	private final Projector	projector;

	public ProjectorCamera(Projector projector) {
		super();
		this.projector = projector;
	}

	@Override
	public Vector3f getPosition() {
		return projector.getPosition();
	}

	@Override
	protected Rotation3f getRotation() {
		return projector.getRotation();
	}

}

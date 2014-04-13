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

	private float calculateFOV(float throwRatio, float aspectRatio) {
		return (float) (2 * Math.atan(throwRatio / aspectRatio));
	}

	@Override
	public float getFieldOfView() {
		return calculateFOV(projector.getThrowRatio(), getAspectRatio());
	}

	@Override
	public Vector3f getPosition() {
		return projector.getPosition();
	}

	@Override
	public int getResolutionHeight() {
		return projector.getResolutionHeight();
	}

	@Override
	public int getResolutionWidth() {
		return projector.getResolutionWidth();
	}

	@Override
	protected Rotation3f getRotation() {
		return projector.getRotation();
	}

}

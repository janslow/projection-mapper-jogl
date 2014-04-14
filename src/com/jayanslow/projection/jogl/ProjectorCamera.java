package com.jayanslow.projection.jogl;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.listeners.ProjectorListener;
import com.jayanslow.projection.world.models.Projector;
import com.jayanslow.projection.world.models.RealObject;
import com.jayanslow.projection.world.models.Rotation3f;

public class ProjectorCamera extends AbstractCamera {

	private final Projector	projector;

	public ProjectorCamera(Projector projector) {
		super();
		this.projector = projector;
		projector.addProjectorListener(new ProjectorListener() {
			@Override
			public void projectorChangeResolution(Projector p, int oldHeight, int oldWidth) {
				fireCameraChangeResolution(oldHeight, oldWidth);
			}

			@Override
			public void projectorChangeThrowRatio(Projector p, float old) {
				fireCameraChangeFieldOfView(calculateFOV(old, getAspectRatio()));
			}

			@Override
			public void projectorResize(Projector p, Vector3f old) {}

			@Override
			public void realObjectMove(RealObject o, Vector3f old) {
				fireCameraMove(old);
			}

			@Override
			public void realObjectRotate(RealObject o, Rotation3f old) {
				fireCameraRotate(old);
			}

		});
	}

	private float calculateFOV(float throwRatio, float aspectRatio) {
		return (float) (2 * Math.atan(1 / throwRatio / aspectRatio));
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

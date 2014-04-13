package com.jayanslow.projection.jogl;

import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.models.Projector;

public class ProjectorVisualiser extends AbstractVisualiser {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= -3666227001668172958L;
	private final ProjectorCamera	camera;
	private RenderMode				renderMode;

	public ProjectorVisualiser(WorldController world, Projector projector, RenderMode renderMode) {
		super(world, String.format("Projector #%s", projector.getProjectorId()), projector.getResolutionHeight(),
				projector.getResolutionWidth());

		camera = new ProjectorCamera(projector);
		this.renderMode = renderMode;
	}

	@Override
	public ProjectorCamera getCamera() {
		return camera;
	}

	@Override
	public RenderMode getRenderMode() {
		return renderMode;
	}

	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}

	@Override
	protected boolean update() {
		return false;
	}

}

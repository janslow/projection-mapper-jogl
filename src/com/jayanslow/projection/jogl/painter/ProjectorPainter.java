package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.world.models.Projector;
import com.jayanslow.projection.world.models.StandardProjector;

public class ProjectorPainter extends AbstractPainter<Projector> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new ProjectorPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f))
			StandardProjectorPainter.registerNested(f);
	}

	public ProjectorPainter(PainterFactory factory) {
		super(Projector.class, factory);
	}

	@Override
	public void paint(GL2 gl, Projector t, RenderMode renderType) {
		getFactory().paint(gl, StandardProjector.class, (StandardProjector) t, renderType);
	}
}

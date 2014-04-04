package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.world.models.Face;
import com.jayanslow.projection.world.models.Screen;

public abstract class AbstractScreenPainter<T extends Screen> extends AbstractRealObjectPainter<T> {

	public AbstractScreenPainter(Class<T> type, PainterFactory factory) {
		super(type, factory, new Color4f(0, 1, 0, 1), new Color4f(0, 1, 0, 1));
	}

	@Override
	protected void paintChildren(GL2 gl, T t, RenderMode renderMode) {
		for (Face f : t.getFaces())
			getFactory().paint(gl, Face.class, f, renderMode);
	}

	@Override
	protected void paintObject(GL2 gl, T t, RenderMode renderMode) {}
}

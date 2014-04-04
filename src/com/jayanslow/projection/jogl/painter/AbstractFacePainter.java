package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.jogl.utils.OpenGLUtils;
import com.jayanslow.projection.world.models.Face;

public abstract class AbstractFacePainter<T extends Face> extends AbstractSimplePainter<T> {

	public AbstractFacePainter(Class<T> type, PainterFactory factory, Color4f strokeColor, Color4f fillColor) {
		super(type, factory, strokeColor, fillColor);
	}

	protected abstract void paintFace(GL2 gl, T t, RenderMode renderMode);

	@Override
	protected void paintObject(GL2 gl, T t, RenderMode renderMode) {
		gl.glPushMatrix();
		OpenGLUtils.translate(gl, t.getPosition());
		OpenGLUtils.rotate(gl, t.getRotation());

		paintFace(gl, t, renderMode);
		gl.glPopMatrix();
	}

}

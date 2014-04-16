package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.jogl.utils.OpenGLUtils;

public abstract class AbstractSimplePainter<T> extends AbstractPainter<T> {
	private final Color4f	strokeColor;

	private final Color4f	fillColor;

	public AbstractSimplePainter(Class<T> type, PainterFactory factory, Color4f strokeColor, Color4f fillColor) {
		super(type, factory);
		this.strokeColor = new Color4f(strokeColor);
		this.fillColor = new Color4f(fillColor);
	}

	public Color4f getFillColor() {
		return new Color4f(fillColor);
	}

	public Color4f getStrokeColor() {
		return new Color4f(strokeColor);
	}

	@Override
	public void paint(GL2 gl, T t, RenderMode renderMode) {
		if (renderMode.getIsFaceVisible())
			paintFace(gl, t, renderMode.getFaceMode());
		if (renderMode.getIsOriginVisible())
			paintOrigin(gl, t);
		if (renderMode.getIsProjectorVisible())
			paintProjector(gl, t);
		if (renderMode.getIsStrokeVisible())
			paintStroke(gl, t);
		if (renderMode.getIsUniverseVisible())
			paintUniverse(gl, t);
		paintChildren(gl, t, renderMode);
	}

	protected void paintChildren(GL2 gl, T t, RenderMode renderMode) {}

	protected void paintFace(GL2 gl, T t, RenderMode.FaceMode faceMode) {}

	protected void paintOrigin(GL2 gl, T t) {}

	protected void paintProjector(GL2 gl, T t) {}

	protected void paintStroke(GL2 gl, T t) {}

	protected void paintUniverse(GL2 gl, T t) {}

	protected void setUpFill(GL2 gl) {
		setUpFill(gl, getFillColor());
	}

	protected void setUpFill(GL2 gl, Color4f fillColor) {
		OpenGLUtils.setLighting(gl, false);
		OpenGLUtils.setPolygonMode(gl, true);
		OpenGLUtils.setColor(gl, fillColor);
	}

	protected void setUpShaded(GL2 gl) {
		setUpShaded(gl, getFillColor());
	}

	protected void setUpShaded(GL2 gl, Color4f fillColor) {
		OpenGLUtils.setLighting(gl, true);
		OpenGLUtils.setPolygonMode(gl, true);

		OpenGLUtils.setMaterial(gl, fillColor);
		OpenGLUtils.setColor(gl, fillColor);
	}

	protected void setUpStroke(GL2 gl) {
		setUpStroke(gl, getStrokeColor());
	}

	protected void setUpStroke(GL2 gl, Color4f strokeColor) {
		OpenGLUtils.setLighting(gl, false);
		OpenGLUtils.setPolygonMode(gl, false);
		OpenGLUtils.setColor(gl, strokeColor);
	}

}

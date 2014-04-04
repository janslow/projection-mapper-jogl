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
		switch (renderMode) {
		case OPAQUE_WIREFRAME:
			renderOpaqueWireframe(gl, t, renderMode, getStrokeColor());
			break;
		case WIREFRAME:
			setWireframe(gl, t, renderMode, getStrokeColor());
			paintObject(gl, t, renderMode);
			break;
		case SOLID:
			setSolid(gl, t, renderMode, getFillColor());
			paintObject(gl, t, renderMode);
			break;
		default:
			throw new UnsupportedOperationException("Unhandled DisplayType " + renderMode.toString());
		}
		paintChildren(gl, t, renderMode);
	}

	protected abstract void paintChildren(GL2 gl, T t, RenderMode renderMode);

	protected abstract void paintObject(GL2 gl, T t, RenderMode renderMode);

	protected void renderOpaqueWireframe(GL2 gl, T t, RenderMode renderMode, Color4f strokeColor) {
		OpenGLUtils.setLighting(gl, false);
		OpenGLUtils.setPolygonMode(gl, true);
		OpenGLUtils.setColor(gl, new Color4f(0, 0, 0, 1));
		paintObject(gl, t, renderMode);
		setWireframe(gl, t, renderMode, strokeColor);
		paintObject(gl, t, renderMode);
	}

	protected void setSolid(GL2 gl, T t, RenderMode renderMode, Color4f fillColor) {
		OpenGLUtils.setLighting(gl, true);
		OpenGLUtils.setPolygonMode(gl, true);

		OpenGLUtils.setMaterial(gl, fillColor);
		OpenGLUtils.setColor(gl, fillColor);
	}

	protected void setWireframe(GL2 gl, T t, RenderMode renderMode, Color4f strokeColor) {
		OpenGLUtils.setLighting(gl, false);
		OpenGLUtils.setPolygonMode(gl, false);
		OpenGLUtils.setColor(gl, strokeColor);
	}

}

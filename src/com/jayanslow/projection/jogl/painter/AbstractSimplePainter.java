package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.vecmath.Color4f;

import com.jayanslow.projection.jogl.RenderMode;

public abstract class AbstractSimplePainter<T> extends AbstractPainter<T> {
	private final Color4f	strokeColor;
	private final Color4f	fillColor;

	public AbstractSimplePainter(Class<T> type, PainterFactory factory, Color4f strokeColor, Color4f fillColor) {
		super(type, factory);
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
	}

	public Color4f getFillColor() {
		return fillColor;
	}

	public Color4f getStrokeColor() {
		return strokeColor;
	}

	@Override
	public void paint(GL2 gl, T t, RenderMode renderMode) {
		switch (renderMode) {
		case WIREFRAME:
			setWireframe(gl);
			break;
		case SOLID:
			setSolid(gl);
			break;
		default:
			throw new UnsupportedOperationException("Unhandled DisplayType " + renderMode.toString());
		}
		paintObject(gl, t, renderMode);
	}

	protected abstract void paintObject(GL2 gl, T t, RenderMode renderMode);

	protected void setSolid(GL2 gl) {
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);

		// Set material properties.
		float[] rgba = new float[4];
		fillColor.get(rgba);

		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
		gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 0.5f);

		gl.glColor4fv(rgba, 0);
	}

	protected void setWireframe(GL2 gl) {
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);

		float[] color = new float[4];
		strokeColor.get(color);
		gl.glColor4fv(color, 0);
	}

}

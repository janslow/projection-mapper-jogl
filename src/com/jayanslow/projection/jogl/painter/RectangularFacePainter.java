package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

import com.jayanslow.projection.jogl.RenderMode.FaceMode;
import com.jayanslow.projection.world.models.RectangularFace;

public class RectangularFacePainter extends AbstractFacePainter<RectangularFace> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new RectangularFacePainter(f));
	}

	public static void registerNested(PainterFactory f) {
		register(f);
	}

	public RectangularFacePainter(PainterFactory factory) {
		super(RectangularFace.class, factory, new Color4f(0, 1, 0, 1), new Color4f(1, 1, 1, 1));
	}

	private void drawRectangle(GL2 gl, Vector2f dim) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glNormal3f(0, 0, 1);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(dim.x, 0, 0);
		gl.glVertex3f(dim.x, dim.y, 0);
		gl.glVertex3f(0, dim.y, 0);
		gl.glEnd();
	}

	@Override
	protected void paintFace(GL2 gl, RectangularFace t, FaceMode faceMode) {
		Vector2f dim = t.getDimensions();

		switch (faceMode) {
		case BLACK:
			setUpFill(gl, new Color4f(0, 0, 0, 1));
			break;
		case SHADED_WHITE:
			setUpShaded(gl);
			break;
		case TEXTURED:
			// TODO: Textured
			break;
		case INVISIBLE:
			return;
		default:
			break;
		}

		drawRectangle(gl, dim);
	}

	@Override
	protected void paintStroke(GL2 gl, RectangularFace t) {
		setUpStroke(gl);

		Vector2f dim = t.getDimensions();
		drawRectangle(gl, dim);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(dim.x, dim.y, 0);
		gl.glVertex3f(dim.x, 0, 0);
		gl.glVertex3f(0, dim.y, 0);
		gl.glEnd();
	}
}

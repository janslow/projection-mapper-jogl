package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;

import com.jayanslow.projection.jogl.Origin;
import com.jayanslow.projection.jogl.utils.OpenGLUtils;
import com.jogamp.opengl.util.gl2.GLUT;

public class OriginPainter extends AbstractSimplePainter<Origin> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new OriginPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		register(f);
	}

	public OriginPainter(PainterFactory factory) {
		super(Origin.class, factory, new Color4f(), new Color4f());
	}

	@Override
	public void paintOrigin(GL2 gl, Origin t) {
		setUpFill(gl);

		float x = t.getSizeX(), y = t.getSizeY(), z = t.getSizeZ();
		float arrowSize = x / 5;

		GLUT glut = new GLUT();

		// Draw x-axis
		OpenGLUtils.setColor(gl, t.getColorX());
		gl.glPushMatrix();
		gl.glRotatef(90, 0, 1, 0);
		gl.glTranslatef(0, 0, x);
		glut.glutSolidCone(arrowSize / 3, arrowSize, 10, 10);
		gl.glPopMatrix();

		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(x, 0, 0);
		gl.glEnd();

		// Draw y-axis
		OpenGLUtils.setColor(gl, t.getColorY());
		gl.glPushMatrix();
		gl.glRotatef(-90, 1, 0, 0);
		gl.glTranslatef(0, 0, y);
		glut.glutSolidCone(arrowSize / 3, arrowSize, 10, 10);
		gl.glPopMatrix();

		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, y, 0);
		gl.glEnd();

		// Draw z-axis
		OpenGLUtils.setColor(gl, t.getColorZ());
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, z);
		glut.glutSolidCone(arrowSize / 3, arrowSize, 10, 10);
		gl.glPopMatrix();

		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, 0, z);
		gl.glEnd();

	}
}

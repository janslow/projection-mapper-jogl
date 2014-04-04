package com.jayanslow.projection.jogl.utils;

import javax.media.opengl.GL2;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.models.Rotation3f;

public class OpenGLUtils {

	public static void drawRectangle(GL2 gl, float x, float y, float z) {
		gl.glBegin(GL2.GL_QUADS);

		// Front
		gl.glNormal3f(0, 0, -1);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(x, 0, 0);
		gl.glVertex3f(x, y, 0);
		gl.glVertex3f(0, y, 0);

		// Back
		gl.glNormal3f(0, 0, 1);
		gl.glVertex3f(0, 0, z);
		gl.glVertex3f(x, 0, z);
		gl.glVertex3f(x, y, z);
		gl.glVertex3f(0, y, z);

		// Left
		gl.glNormal3f(-1, 0, 0);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, y, 0);
		gl.glVertex3f(0, y, z);
		gl.glVertex3f(0, 0, z);

		// Right
		gl.glNormal3f(1, 0, 0);
		gl.glVertex3f(x, 0, 0);
		gl.glVertex3f(x, y, 0);
		gl.glVertex3f(x, y, z);
		gl.glVertex3f(x, 0, z);

		// Top
		gl.glNormal3f(0, 1, 0);
		gl.glVertex3f(0, y, 0);
		gl.glVertex3f(x, y, 0);
		gl.glVertex3f(x, y, z);
		gl.glVertex3f(0, y, z);

		// Bottom
		gl.glNormal3f(0, -1, 0);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(x, 0, 0);
		gl.glVertex3f(x, 0, z);
		gl.glVertex3f(0, 0, z);

		gl.glEnd();
	}

	public static float getValuef(GL2 gl, int param) {
		float[] getValues = new float[1];
		gl.glGetFloatv(param, getValues, 0);
		float originalWidth = getValues[0];
		return originalWidth;
	}

	public static void inverseRotate(GL2 gl, Rotation3f rotation) {
		gl.glRotatef(-rotation.z, 0, 0, 1);
		gl.glRotatef(-rotation.y, 0, 1, 0);
		gl.glRotatef(-rotation.x, 1, 0, 0);
	}

	public static void negativeTranslate(GL2 gl, Vector3f vector) {
		Vector3f negated = new Vector3f(vector);
		negated.negate();
		translate(gl, negated);
	}

	public static void rotate(GL2 gl, Rotation3f rotation) {
		gl.glRotatef(rotation.x, 1, 0, 0);
		gl.glRotatef(rotation.y, 0, 1, 0);
		gl.glRotatef(rotation.z, 0, 0, 1);
	}

	public static void setColor(GL2 gl, Color3f color) {
		gl.glColor4f(color.x, color.y, color.z, 1);
	}

	public static void setColor(GL2 gl, Color4f color) {
		gl.glColor4f(color.x, color.y, color.z, color.w);
	}

	public static void setVertex(GL2 gl, Vector3f v) {
		gl.glVertex3f(v.x, v.y, v.z);
	}

	public static void translate(GL2 gl, Vector3f vector) {
		gl.glTranslatef(vector.x, vector.y, vector.z);
	}
}

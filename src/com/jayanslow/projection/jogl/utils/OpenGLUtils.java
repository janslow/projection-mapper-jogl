package com.jayanslow.projection.jogl.utils;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.models.Rotation3f;

public class OpenGLUtils {

	public static void drawCuboid(GL2 gl, float x, float y, float z) {
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

	public static float[] getRGBA(Color4f fillColor) {
		float[] rgba = new float[4];
		fillColor.get(rgba);
		return rgba;
	}

	public static float getValuef(GL2 gl, int param) {
		float[] getValues = new float[1];
		gl.glGetFloatv(param, getValues, 0);
		float originalWidth = getValues[0];
		return originalWidth;
	}

	public static void inverseRotate(GL2 gl, Rotation3f rotation) {
		gl.glRotatef(-(float) Math.toDegrees(rotation.z), 0, 0, 1);
		gl.glRotatef(-(float) Math.toDegrees(rotation.x), 1, 0, 0);
		gl.glRotatef(-(float) Math.toDegrees(rotation.y), 0, 1, 0);
	}

	public static void negativeTranslate(GL2 gl, Vector3f vector) {
		Vector3f negated = new Vector3f(vector);
		negated.negate();
		translate(gl, negated);
	}

	public static void rotate(GL2 gl, Rotation3f rotation) {
		gl.glRotatef((float) Math.toDegrees(rotation.y), 0, 1, 0);
		gl.glRotatef((float) Math.toDegrees(rotation.x), 1, 0, 0);
		gl.glRotatef((float) Math.toDegrees(rotation.z), 0, 0, 1);
	}

	public static void setColor(GL2 gl, Color3f color) {
		gl.glColor4f(color.x, color.y, color.z, 1);
	}

	public static void setColor(GL2 gl, Color4f color) {
		gl.glColor4f(color.x, color.y, color.z, color.w);
	}

	public static void setMaterial(GL2 gl, Color4f fillColor) {
		setMaterial(gl, getRGBA(fillColor));
	}

	public static void setMaterial(GL2 gl, Color4f ambientColor, Color4f specularColor, int shininess) {
		setMaterialSpecular(gl, getRGBA(ambientColor));
		setMaterialAmbient(gl, getRGBA(specularColor));
		setMaterialShininess(gl, shininess);
	}

	public static void setMaterial(GL2 gl, float[] rgba) {
		setMaterialSpecular(gl, rgba);
		setMaterialAmbient(gl, rgba);
		setMaterialShininess(gl, 0.5f);
	}

	public static void setMaterialAmbient(GL2 gl, Color4f color) {
		setMaterialAmbient(gl, getRGBA(color));

	}

	public static void setMaterialAmbient(GL2 gl, float[] rgba) {

		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
	}

	public static void setMaterialShininess(GL2 gl, float shininess) {
		gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, shininess);
	}

	public static void setMaterialSpecular(GL2 gl, Color4f color) {
		setMaterialSpecular(gl, getRGBA(color));
	}

	public static void setMaterialSpecular(GL2 gl, float[] rgba) {
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
	}

	public static void setPolygonMode(GL2 gl, boolean fill) {
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, fill ? GL2.GL_FILL : GL2.GL_LINE);
	}

	public static void setVertex(GL2 gl, Vector3f v) {
		gl.glVertex3f(v.x, v.y, v.z);
	}

	public static void translate(GL2 gl, Vector3f vector) {
		gl.glTranslatef(vector.x, vector.y, vector.z);
	}

	public static void setLighting(GL2 gl, boolean enable) {
		if (enable) {
			gl.glEnable(GL2.GL_LIGHTING);
			gl.glEnable(GL2.GL_LIGHT0);
		} else {
			gl.glDisable(GL2.GL_LIGHTING);
			gl.glDisable(GL2.GL_LIGHT0);
		}
	}
}

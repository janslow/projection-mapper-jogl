package com.jayanslow.projection.jogl.utils;

import javax.vecmath.Matrix3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class VectorOperations {
	public static float counterClockwiseAngle(Vector2f a, Vector2f b) {
		float wedge = (a.x * b.y) - (a.y * b.x);
		return (float) Math.atan2(wedge, a.dot(b));
	}

	public static Matrix3f getCrossProductMatrix(Vector3f v) {
		return new Matrix3f(0, -v.z, v.y, v.z, 0, -v.x, -v.y, v.x, 0);
	}

	public static Matrix3f getIdentityMatrix() {
		return new Matrix3f(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}

	public static Matrix3f getRotationMatrix(Vector3f axis, float angle) {
		float c = (float) Math.cos(angle);

		Matrix3f identityMatrix = getIdentityMatrix();
		identityMatrix.mul(c);

		Matrix3f crossProduct = getCrossProductMatrix(axis);
		crossProduct.mul((float) Math.sin(angle));

		Matrix3f tensorProduct = getTensorProduct(axis);
		tensorProduct.mul(1 - c);

		identityMatrix.add(crossProduct);
		identityMatrix.add(tensorProduct);
		return identityMatrix;
	}

	public static Matrix3f getTensorProduct(Vector3f v) {
		Matrix3f tensorProduct = new Matrix3f();
		Vector3f row0 = new Vector3f(v);
		row0.scale(v.x);
		tensorProduct.setRow(0, row0);
		Vector3f row1 = new Vector3f(v);
		row1.scale(v.y);
		tensorProduct.setRow(1, row1);
		Vector3f row2 = new Vector3f(v);
		row2.scale(v.z);
		tensorProduct.setRow(2, row2);
		return tensorProduct;
	}

	private static Vector3f inverseRotate(Vector3f v, float angleX, float angleY, float angleZ) {
		v = rotate(v, new Vector3f(0, 0, 1), -angleZ);
		v = rotate(v, new Vector3f(0, 1, 0), -angleY);
		return rotate(v, new Vector3f(1, 0, 0), -angleX);
	}

	public static Vector3f inverseRotate(Vector3f v, Tuple3f rotation) {
		return inverseRotate(v, rotation.x, rotation.y, rotation.z);
	}

	public static Vector3f normalVector(Vector3f a, Vector3f b) {
		Vector3f n = new Vector3f();
		n.cross(a, b);
		n.normalize();
		return n;
	}

	public static Vector2f rotate(Vector2f v, float theta) {
		float c = (float) Math.cos(theta), s = (float) Math.sin(theta);
		return new Vector2f(v.x * c - v.y * s, v.x * s + v.y * c);
	}

	public static Vector3f rotate(Vector3f v, float angleX, float angleY, float angleZ) {
		v = rotate(v, new Vector3f(1, 0, 0), angleX);
		v = rotate(v, new Vector3f(0, 1, 0), angleY);
		return rotate(v, new Vector3f(0, 0, 1), angleZ);
	}

	public static Vector3f rotate(Vector3f v, Tuple3f rotation) {
		return rotate(v, rotation.x, rotation.y, rotation.z);
	}

	public static Vector3f rotate(Vector3f vector, Vector3f axis, float angle) {
		Matrix3f m = getRotationMatrix(axis, angle);

		Vector3f result = new Vector3f(vector);
		m.transform(result);
		return result;
	}
}

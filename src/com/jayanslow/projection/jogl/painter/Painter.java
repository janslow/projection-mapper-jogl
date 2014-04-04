package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.world.models.RenderMode;

public interface Painter<T> {
	/**
	 * Type of object that this painter can paint
	 * 
	 * @return Class of T
	 */
	public Class<T> getTargetClass();

	/**
	 * Paints an object
	 * 
	 * @param gl
	 *            OpenGL canvas to paint on
	 * @param t
	 *            Object to paint
	 * @param display
	 *            Object display type
	 */
	public void paint(GL2 gl, T t, RenderMode type);
}

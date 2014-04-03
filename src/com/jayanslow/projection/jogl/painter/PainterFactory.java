package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.world.models.RenderMode;
import com.jayanslow.projection.world.models.Universe;

public interface PainterFactory {
	/**
	 * Adds a painter to the factory
	 * 
	 * @param painter
	 *            Painter to add
	 * @return True if painter is added (i.e., it does not already exist), otherwise false
	 */
	public <T> boolean addPainter(Painter<T> painter);

	/**
	 * Checks if the factory can paint a class
	 * 
	 * @param type
	 *            Type to check
	 * @return True if the type can be painted, otherwise false
	 */
	public <T> boolean containsPainter(Class<T> type);

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
	public <T> void paint(GL2 gl, Class<T> type, T t, RenderMode display);

	/**
	 * Paints a universe
	 * 
	 * @param gl
	 *            OpenGL canvas to paint on
	 * @param universe
	 *            Universe to paint
	 */
	public void paint(GL2 gl, Universe universe);
}

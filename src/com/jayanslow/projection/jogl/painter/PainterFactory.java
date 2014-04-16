package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.texture.models.ImageTexture;
import com.jayanslow.projection.world.models.Face;

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
	 * Gets the image texture to display on a face
	 * 
	 * @param face
	 *            Face to lookup
	 * @return ImageTexture to display, or null if there is no such image texture
	 */
	public ImageTexture getFaceTexture(Face face);

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
}

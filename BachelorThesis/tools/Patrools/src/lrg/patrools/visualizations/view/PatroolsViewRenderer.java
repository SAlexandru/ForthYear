/*
 *  JMondrian
 *  Copyright (c) 2007-2008 Loose Research Group
 *  Petru Florin Mihancea - petru.mihancea@cs.upt.ro
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package lrg.patrools.visualizations.view;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.jMondrian.view.ShapeElementFactory;
import lrg.jMondrian.view.ViewRendererInterface;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.jdt.internal.ui.text.java.hover.SourceViewerInformationControl;

import plugins.Wrapper;
import view.IStatusLineUpdater;

public class PatroolsViewRenderer implements ViewRendererInterface {
	
	protected ShapeJavaFactory factory = new ShapeJavaFactory();
	private double zoom = 1;
	private final String title;
	private Panel contents=new Panel();

	private final class ShapeJavaFactory implements ShapeElementFactory {

		private ScalableFreeformLayeredPane container = new ScalableFreeformLayeredPane();
		private Label statusBar= new Label("default");
		private int width=0;
		private int height=0;
		private List<Object> m = new ArrayList<Object>();
		private List<String> d = new ArrayList<String>();
		private ShapeJavaFactory() {
			container.setBackgroundColor(ColorConstants.white);
		}
		private void setColorAndAdds(Figure f, int color, Object ent, String descr){
			Color SWTColor = new Color(null,(color>>16)&0xFF,(color>>8)&0xFF,color&0xFF);
			f.setBackgroundColor(SWTColor);
			f.setForegroundColor(SWTColor);
			this.container.add(f);
			this.m.add(ent);
			this.d.add(descr);
		}

		public ArrayList<Object> getEntities(){
			return (ArrayList<Object>) m;
		}

		public void addRectangle(Object ent, String descr, int x1, int y1, int width, int heigth, int color, boolean border) {
			if(border)
				this.addRectangle(ent, descr, x1, y1, width, heigth, color, java.awt.Color.BLACK.getRGB());
			else
				this.addRectangle(ent, descr, x1, y1, width, heigth, color, Integer.MAX_VALUE);
		}

		public void addRectangle(Object ent, String descr, int x1, int y1, int width, int heigth, int color, int frameColor) {
			RectangleFigure newRectangle=new RectangleFigure();
			newRectangle.setLocation(new Point(x1,y1));
			newRectangle.setSize(width, heigth);
			checkSize(x1+width, y1+heigth);
			this.setColorAndAdds(newRectangle, color, ent, descr);
			if(frameColor != Integer.MAX_VALUE){
				Border lineBorder=new LineBorder(new Color(null,(frameColor>>16)&0xFF,(frameColor>>8)&0xFF,frameColor&0xFF));
				newRectangle.setBorder(lineBorder);
			}
		}

		public void addEllipse(Object ent, String descr, int x1, int y1, int width, int heigth, int color, boolean border) {
			if(border)
				this.addEllipse(ent, descr, x1, y1, width, heigth, color, java.awt.Color.BLACK.getRGB());
			else
				this.addEllipse(ent, descr, x1, y1, width, heigth, color, Integer.MAX_VALUE);
		}

		public void addEllipse(Object ent, String descr, int x1, int y1, int width, int heigth, int color, int frameColor) {
			Ellipse newEllipse=new Ellipse();
			newEllipse.setLocation(new Point(x1,y1));
			newEllipse.setSize(width, heigth);
			this.setColorAndAdds(newEllipse, color, ent, descr);
			if(frameColor != Integer.MAX_VALUE){
				Border lineBorder=new LineBorder(new Color(null,(frameColor>>16)&0xFF,(frameColor>>8)&0xFF,frameColor&0xFF));
				newEllipse.setBorder(lineBorder);
			}
		}

		public void addLine(Object ent, String descr, int x1, int y1, int x2, int y2, int color, boolean oriented) {
			Polyline newLine=new Polyline();
			newLine.setStart(new Point(x1,y1));
			newLine.setEnd(new Point(x2,y2));
			checkSize(x1, y1);
			checkSize(x2, y2);
			this.setColorAndAdds(newLine, color, ent, descr);
		}

		public void addText(Object ent, String descr, String text, int x1, int y1, int color) {
			Label newLabel= new Label();
			newLabel.setText(text);
			newLabel.setLocation(new Point(x1,y1));
			this.setColorAndAdds(newLabel, color, ent, descr);
		}

		public void update() {
			container.repaint();
		}

		public IFigure getContents(){
			return this.container;
		}

		private void checkSize(int x, int y){
			boolean ok = false;
			if (x>width){
				ok = true;
				width = x;
			}
			if (y>height){
				ok = true;
				height = y;
			}
			if (ok) container.setSize(width, height);
		}

		public String findStatusInformation(int x, int y) {
			int index=0;

			for(Figure f :  (List<Figure>)this.container.getChildren()){
				if (f.containsPoint(x,y))
					return d.get(index);
				index++;
			}
			return "";
		}

		public Object findEntity(int x, int y) {
			double scale = container.getScale();
			x = (int)(x / scale);
			y = (int)(y / scale);
			for (int i = container.getChildren().size()-1;i>=0;i--){
				Figure f = (Figure) container.getChildren().get(i);
				if (f.containsPoint(x,y))
					return m.get(i);
			}
			return null;
		}
		public void printM(){
			Iterator it = m.iterator();
			while (it.hasNext()){
				System.out.println(it.next().getClass());
			}
		}

		public void addPolyLine(Object ent, String descr, List<Integer> x, List<Integer> y) {
			for(int i=1; i < x.size(); i++){
				this.addLine(ent, descr, x.get(i-1), y.get(i-1), x.get(i), y.get(i),java.awt.Color.LIGHT_GRAY.getRGB(), false);
			}
			this.addLine(ent, descr, x.get(0), y.get(0), x.get(x.size()-1), y.get(x.size()-1),java.awt.Color.LIGHT_GRAY.getRGB(), false);
		}

		public void zoom(double zoom) {	
			container.setScale(zoom);
			if(container.getParent() != null){
				Rectangle d=container.getFreeformExtent();
				double width=d.width*zoom;
				double height=d.height*zoom;
				container.setSize((int)width, (int)height);

				Rectangle r = container.getClientArea().getResized((int)(zoom*15),(int)(zoom*15));
				container.setFreeformBounds(r);
			}

		}

		public void createNewContainer() {
			container = new ScalableFreeformLayeredPane();

		}

		public Label getStatusBar(){
			return this.statusBar;
		}
		public void init() {
			zoom(container.getScale());
		}
		public void addCubicCurve(Object arg0, String arg1, double arg2,
				double arg3, double arg4, double arg5, double arg6,
				double arg7, double arg8, double arg9, int arg10, boolean arg11) {
			// TODO Auto-generated method stub
			
		}
		public void addPolyLine(Object arg0, String arg1, List<Integer> arg2,
				List<Integer> arg3, int arg4, boolean arg5, boolean arg6) {
			// TODO Auto-generated method stub
			
		}
		public void addQuadCurve(Object arg0, String arg1, double arg2,
				double arg3, double arg4, double arg5, double arg6,
				double arg7, int arg8, boolean arg9) {
			// TODO Auto-generated method stub
			
		}

	}

	public ShapeElementFactory getShapeFactory() {
		return factory;
	}

	public PatroolsViewRenderer(String title) {
		this.title = title;
	}

	public void setTitle(String newTitle) {}

	public PatroolsViewRenderer() {
		this("lrg.jMondrian");
	}

	private Class handlerPrototype = PatroolsRightClickHandler.class;
	
	public void setRightClickPrototype(Class handlerPrototype) {
		this.handlerPrototype = handlerPrototype;
	}
	
	private PatroolsRightClickHandler createRightClickHandler(MouseEvent e, ShapeElementFactory factory, PatroolsViewRenderer theRenderer, Control control) {
		try {
			return (PatroolsRightClickHandler) handlerPrototype.getConstructors()[0].newInstance(e, factory, theRenderer, control);
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				return (PatroolsRightClickHandler) PatroolsRightClickHandler.class.getConstructors()[0].newInstance(e, factory, theRenderer, control);
			} catch (Exception e2) {
				e2.printStackTrace();
				return null;
			}
		}
	}
	protected PatroolsRightClickHandler handler;

	protected PatroolsDoubleClickHandler rightClickHandler;

	public void create(final Control control, final boolean handleRightClick, final boolean handleHovering, final String viewID) {
		factory.init();
		factory.getContents().addMouseListener(new MouseListener(){

			public void mouseDoubleClicked(MouseEvent arg0) {
				PatroolsDoubleClickHandler doubleClickHandler = new PatroolsDoubleClickHandler(arg0, factory);
				doubleClickHandler.handleDoubleClick();
			}

			public void mousePressed(MouseEvent arg0) {
				cleanup();
				if (handleRightClick){
					if (arg0.button == 3){
						handler = createRightClickHandler(arg0, factory, PatroolsViewRenderer.this,control);
						handler.handleRightClick();
					}
				}
			}

			public void mouseReleased(MouseEvent arg0) {
			}

		});

		factory.getContents().addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseHover(final MouseEvent arg0) {
				if (handleHovering){
					Display.getDefault().asyncExec(new Runnable(){
						
						public void run() {
							final Object ent = factory.findEntity(arg0.x, arg0.y);
							if (ent instanceof Wrapper){
								entity = (Wrapper)ent;
								org.eclipse.swt.graphics.Point p = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getWorkbench().getDisplay().getCursorLocation();
								di = new SourceViewerInformationControl(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), true, SWT.NONE, null);
								di.setInformation((String)entity.getProperty("SourceCode").getValue());
								org.eclipse.swt.graphics.Point res = di.computeSizeHint();
								di.setLocation(p);
								di.setSize(res.x , res.y > 200 ? 200 : res.y);
								di.setFocus();
								di.setVisible(true);
							}
						}
					});
				}
			}

			public void mouseMoved(MouseEvent arg0) {
				if (di!=null){
					di.setVisible(false);
					di.dispose();
					di = null;
				}

				final Object ent = factory.findEntity(arg0.x, arg0.y);

				if (ent instanceof Wrapper){
					entity = (Wrapper)ent;
					updateStatusBar(entity.getName(), viewID);
				}
				else
					updateStatusBar(title, viewID);					
			}

		});
	}

	public void cleanup() {
		if (di!=null){
			di.setVisible(false);
			di.dispose();
			di = null;
		}
	}

	public void refresh() {
		factory.update();
	}

	public void clear() {		
		factory.createNewContainer();
	}

	public void deleteFrame(){
		factory.getContents().setVisible(false);

	}

	private SourceViewerInformationControl di = null;
	private AbstractEntityInterface entity = null;


	public void zoomIn(){ 
		zoom += 0.2;
		factory.zoom(zoom);
		refresh();
	}

	public void zoomOut(){
		zoom -= 0.2;
		factory.zoom(zoom);
		refresh();
	}

	public void fitToWindow(FigureCanvas canvas) {
		double widthRatio;
		double heightRatio;

		int oldWidth=factory.getContents().getSize().width;
		int oldHeight=factory.getContents().getSize().height;

		widthRatio = ((double)(canvas.getViewport().getSize().width) / ((double)oldWidth));
		heightRatio = ((double)(canvas.getViewport().getSize().height) / ((double)oldHeight));
		if(widthRatio < heightRatio)
			zoom=widthRatio;
		else 
			zoom=heightRatio;
		((ScalableFreeformLayeredPane)factory.getContents()).setScale(zoom);
	}

	public void setSizeRelativeTo(int x1, int y1, int x, int y) {

		double procX = (double)x/(double)x1;
		double procY = (double)y/(double)y1;
		double proc = Math.min(procX, procY);
		if (proc<1){
			zoom -= (1-proc);
			factory.zoom(zoom);
		}

	}

	public void save(){
		FileDialog saveDialog = new FileDialog(
				Display.getDefault().getActiveShell(), SWT.SAVE);
		saveDialog.setFilterExtensions(new String[] { "*.html" });
		saveDialog.setFilterNames(new String[] { "HTML format (*.html)" });
		String filePath = saveDialog.open();
		if (filePath == null || filePath.trim().length() == 0) {
			return;
		}

		IFigure fig = factory.getContents();
		try {
			int imageType = -1;
			if (!filePath.contains("."))
				filePath += ".html";
			if (filePath.toLowerCase().endsWith(".html")) {
				imageType = SWT.IMAGE_PNG;
			}
			else {
				MessageDialog.openInformation(Display.getDefault().getActiveShell(),
						"Error", "Unrecognized filename extension(use html)");
				return;
			}

			byte[] imageData = createImage(fig, imageType);

			if (filePath.toLowerCase().endsWith(".html")){
				filePath = filePath.substring(0, filePath.length()-4) + "png";
			}
			FileOutputStream outStream = new FileOutputStream(filePath);
			outStream.write(imageData);
			outStream.flush();
			outStream.close();

			createHtmlFile(filePath);
		} catch (Throwable e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Error", "Failed to export image: " + e.getMessage());
			return;
		}
	}

	private void createHtmlFile(String filePath) {
		try{
			String file ="";
			file+= "<DIV ALIGN=CENTER> " +
			"<MAP NAME=\"map1\">";


			ArrayList<Object> entities = ((ShapeJavaFactory)factory).getEntities();
			for (int k=entities.size()-1;k>=0;k--){
				if (entities.get(k) instanceof Wrapper){
					Figure contents = (Figure) ((ShapeJavaFactory)factory).getContents();
					if (((Figure)contents.getChildren().get(k)).getBounds().width != 0){
						Rectangle rectangle= ((Figure)contents.getChildren().get(k)).getBounds();
						Point p =  ((Figure)contents.getChildren().get(k)).getLocation();
						file +="<AREA TITLE=\"";
						file +=entities.get(k).toString();
						file +="\" SHAPE=RECT COORDS=\"";
						file +=	(p.x*zoom)+","+(p.y*zoom)+","+((int)(p.x+rectangle.width)*zoom)+","+((int)(p.y+rectangle.height)*zoom)+"\" >\n";

					}
				}
			}
			file+="</MAP> "+
			"<IMG SRC=\"file:///";
			file+=filePath;
			file+="\"USEMAP=\"#map1\"><BR> </DIV>";

			filePath = filePath.substring(0, filePath.length()-4) + ".html";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(file);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}

	public int getWidth(){
		return factory.getContents().getSize().width;
	}

	public int getHeigth(){
		return factory.getContents().getSize().height;
	}

	public IFigure getFigure() {
		return factory.getContents();

	}

	public IFigure getContents(){
		ScalableFreeformLayeredPane SFLPane;

		SFLPane = (ScalableFreeformLayeredPane)(this.getFigure());
		SFLPane.setFreeformBounds(new Rectangle(SFLPane.getClientArea()));

		contents.setLayoutManager(new BorderLayout());

		contents.add(SFLPane);
		contents.setConstraint(SFLPane, BorderLayout.CENTER);

		return contents; 	
	}

	/***
	 * Returns the bytes of an encoded image for the specified
	 * IFigure in the specified format.
	 *
	 * @param figure the Figure to create an image for.
	 * @param format one of SWT.IMAGE_BMP, SWT.IMAGE_BMP_RLE, SWT.IMAGE_GIF
	 *          SWT.IMAGE_ICO, SWT.IMAGE_JPEG, or SWT.IMAGE_PNG
	 * @return the bytes of an encoded image for the specified Figure
	 */
	private byte[] createImage(IFigure figure, int format) throws Exception {
		if (needsAdjusting(figure)){
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Notification", "Image too large to save, resizing to fit inside 10 megapixels");
			figure = adjustImage(figure);
		}
		Device device = Display.getDefault();
		Rectangle r = figure.getBounds();
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		Image image = null;
		GC gc = null;
		Graphics g = null;
		Exception error = null;
		try {
			image = new Image(device, r.width, r.height);
			gc = new GC(image);
			g = new SWTGraphics(gc);
			g.translate(r.x * -1, r.y * -1);

			figure.paint(g);

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(result, format);
		} catch (Exception ex) {
			error = ex;
		} finally {
			if (g != null) {
				g.dispose();
			}
			if (gc != null) {
				gc.dispose();
			}
			if (image != null) {
				image.dispose();
			}
		}
		if (error != null) {
			throw error;
		}
		return result.toByteArray();
	}

	private boolean needsAdjusting(IFigure figure) {
		Rectangle bounds = figure.getBounds();
		return bounds.width*bounds.height > 10000000;
	}

	private IFigure adjustImage(IFigure figure) {
		if (needsAdjusting(figure)){
			zoomOut();
			return adjustImage(factory.getContents());
		}
		else
			return figure;
	}

	public String toString(){
		return this.title;
	}

	private void updateStatusBar(String newText, String viewID){
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ViewPart view = (ViewPart)page.findView(viewID);
		if (view instanceof IStatusLineUpdater){
			IStatusLineUpdater updater = (IStatusLineUpdater)view;
			updater.updateStatusText(newText);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setPreferredSize(int width, int heigth) {}

}


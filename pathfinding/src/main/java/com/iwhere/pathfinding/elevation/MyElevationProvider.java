package com.iwhere.pathfinding.elevation;

import com.graphhopper.reader.ReaderNode;
import com.graphhopper.reader.dem.ElevationProvider;
import com.graphhopper.storage.DAType;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.geometry.DirectPosition2D;
import org.opengis.referencing.operation.TransformException;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MyElevationProvider implements ElevationProvider {

    String filePath;

    GridCoverage2D coverage=null;

    Raster eleData;

    public MyElevationProvider(String filePath){
        this.filePath=filePath;
    }

    public void initialize(){
        File file=new File(filePath);
        AbstractGridFormat format = GridFormatFinder.findFormat( file );
        GridCoverage2DReader reader = format.getReader( file );
        try {
            coverage = (GridCoverage2D) reader.read(null);
            RenderedImage image = coverage.getRenderedImage();
            eleData=image.getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public double getEle(double lat, double lon) {

        GridGeometry2D gg = coverage.getGridGeometry();
        DirectPosition2D posWorld = new DirectPosition2D(lon,lat);
        GridCoordinates2D posGrid = null;
        try {
            posGrid = gg.worldToGrid(posWorld);
            double[] pixel=new double[1];
            double value[]=eleData.getPixel(posGrid.x,posGrid.y,pixel);
            return value[0];
        } catch (TransformException e) {
            return 0;
            //throw new RuntimeException(e);
        }
    }


    @Override
    public boolean canInterpolate() {
        return true;
    }





    @Override
    public void release() {

    }


}

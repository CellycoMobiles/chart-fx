package de.gsi.chart.samples;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gsi.chart.samples.RunChartSamples.MyButton;
import de.gsi.chart.viewer.DataView;
import de.gsi.chart.viewer.DataViewer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author rstein
 */
public class AllChartSamples extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllChartSamples.class);
    protected static final String FONT_AWESOME = "FontAwesome";
    protected static final int FONT_SIZE = 20;

    @Override
    public void start(final Stage primaryStage) {
        final SplitPane root = new SplitPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setDividerPosition(0, 0.1);
        DataViewer viewer = new DataViewer();

        root.getItems().addAll(new TitledPane("controls", viewer.getToolBar()), viewer);

        List<Application> applications = new ArrayList<>();
        applications.add(new AxisRangeScalingSample());
        applications.add(new CategoryAxisSample());
        applications.add(new ChartAnatomySample());
        applications.add(new ChartIndicatorSample());
        applications.add(new ContourChartSample());
        applications.add(new CustomColourSchemeSample());
        applications.add(new CustomFragmentedRendererSample());
        applications.add(new DataViewerSample());
        applications.add(new EditDataSetSample());
        applications.add(new ErrorDataSetRendererSample());
        applications.add(new ErrorDataSetRendererStylingSample());
        applications.add(new FxmlSample());
        applications.add(new GridRendererSample());
        applications.add(new HexagonSamples());
        applications.add(new Histogram2DimSample());
        applications.add(new HistogramSample());
        applications.add(new HistoryDataSetRendererSample());
        applications.add(new LabelledMarkerSample());
        applications.add(new LogAxisSample());
        applications.add(new MetaDataRendererSample());
        applications.add(new MountainRangeRendererSample());
        applications.add(new MultipleAxesSample());
        applications.add(new NotANumberSample());
        applications.add(new PolarPlotSample());
        applications.add(new RollingBufferSample());
        applications.add(new RollingBufferSortedTreeSample());
        applications.add(new ScatterAndBubbleRendererSample());
        applications.add(new SimpleChartSample());
        applications.add(new TimeAxisRangeSample());
        applications.add(new TimeAxisSample());
        applications.add(new TransposedDataSetSample());
        applications.add(new ValueIndicatorSample());
        applications.add(new WriteDataSetToFileSample());

        for (Application app : applications) {
            if (app == null) {
                continue;
            }
            final Glyph customViewIcon = new Glyph(FONT_AWESOME, FontAwesome.Glyph.USERS).size(FONT_SIZE);
            viewer.getViews().add(new DataView(app.getClass().getSimpleName(), customViewIcon, getPane(app)));
        }

        viewer.showListStyleDataViewProperty().set(true);

        final Scene scene = new Scene(root, 1500, 900);
        primaryStage.setTitle(getClass().getSimpleName());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(evt -> Platform.exit());
    }

    public static Pane getPane(final Application app) {
        try {
            Stage localStage = new Stage();
            app.start(localStage);
            localStage.hide();
            localStage.close();

            Parent root = localStage.getScene().getRoot();
            if (root instanceof Pane) {
                return (Pane) root;
            }
            return new StackPane(root);
        } catch (Exception e) {
            LOGGER.atError().setCause(e).log("application = " + app.getClass().getSimpleName());
        }

        return new StackPane(new Label("no node for " + app.getClass().getSimpleName()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}

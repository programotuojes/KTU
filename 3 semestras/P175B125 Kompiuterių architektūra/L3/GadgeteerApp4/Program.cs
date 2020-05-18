using System;
using System.Collections;
using System.Threading;
using Microsoft.SPOT;
using Microsoft.SPOT.Presentation;
using Microsoft.SPOT.Presentation.Controls;
using Microsoft.SPOT.Presentation.Media;
using Microsoft.SPOT.Presentation.Shapes;
using Microsoft.SPOT.Touch;

using Gadgeteer.Networking;
using GT = Gadgeteer;
using GTM = Gadgeteer.Modules;
using Gadgeteer.Modules.GHIElectronics;

namespace GadgeteerApp4
{
    public partial class Program
    {
        private Window window;
        private Canvas canvas;

        private Font header = Resources.GetFont(Resources.FontResources.consolas_24);
        private Font line = Resources.GetFont(Resources.FontResources.Arial_18);

        private Text column1;
        private Text column2;
        private Text column3;

        private int currentRow = 0;
        private Text[] rowNr = new Text[5];
        private Text[] xValues = new Text[5];
        private Text[] yValues = new Text[5];

        void ProgramStarted()
        {
            SetupWindow();

            tempHumidity.MeasurementInterval = 500;
            tempHumidity.MeasurementComplete += new TempHumidity.MeasurementCompleteEventHandler(sensor_MeasurementComplete);
            tempHumidity.StartTakingMeasurements();
        }

        void sensor_MeasurementComplete(TempHumidity sender, TempHumidity.MeasurementCompleteEventArgs e)
        {
            rowNr[currentRow % 5].TextContent = (currentRow + 1).ToString();
            xValues[currentRow % 5].TextContent = e.Temperature.ToString("F2");
            yValues[currentRow % 5].TextContent = e.RelativeHumidity.ToString("F2");

            currentRow++;
        }

        void SetupWindow()
        {
            window = displayT43.WPFWindow;
            canvas = new Canvas();
            window.Child = canvas;

            canvas.Children.Add(new Image(Resources.GetBitmap(Resources.BitmapResources.table)));

            column1 = new Text(header, "Nr.");
            column2 = new Text(header, "Temp");
            column3 = new Text(header, "Humidity");

            canvas.Children.Add(column1);
            Canvas.SetLeft(column1, 25);
            Canvas.SetTop(column1, 15);

            canvas.Children.Add(column2);
            Canvas.SetLeft(column2, 110);
            Canvas.SetTop(column2, 15);

            canvas.Children.Add(column3);
            Canvas.SetLeft(column3, 310);
            Canvas.SetTop(column3, 15);

            for (int i = 0; i < 5; i++)
            {
                rowNr[i] = new Text(line, "");
                canvas.Children.Add(rowNr[i]);
                Canvas.SetLeft(rowNr[i], 22);
                Canvas.SetTop(rowNr[i], 69 + 42 * i);

                xValues[i] = new Text(line, "");
                canvas.Children.Add(xValues[i]);
                Canvas.SetLeft(xValues[i], 122);
                Canvas.SetTop(xValues[i], 69 + 42 * i);

                yValues[i] = new Text(line, "");
                canvas.Children.Add(yValues[i]);
                Canvas.SetLeft(yValues[i], 314);
                Canvas.SetTop(yValues[i], 69 + 42 * i);
            }
        }
    }
}

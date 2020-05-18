/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab3.gui;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 *
 * @param <S>
 * @param <T>
 */
public abstract class MapTable<S, T> extends TableView<S> {

    public MapTable() {
        super();
        setPlaceholder(new Label(""));
    }

    public void formTable(int numberOfColumns, int colWidth) {
        getColumns().clear();
        for (int i = 0; i < numberOfColumns; i++) {
            String columnTitle = (i == 0) ? "#" : ((i % 2 == 0) ? "(" + ((i - 1) / 2 + 1) + ")" : "");
            TableColumn<S, T> col = new TableColumn<>(columnTitle);
            col.setSortable(false);
            if (i == 0) {
                // Pirmo stulpelio plotis
                col.setPrefWidth(50);
            } else {
                // Nelyginio stulpelio (su rodykle -->) plotis - 35
                col.setPrefWidth(i % 2 != 0 ? 35 : colWidth);
            }
            final boolean toAlign = (i == 0 || i % 2 != 0);
            col.setCellFactory(p -> new TableCell<S, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.toString().isEmpty()) {
                        setText(null);
                        setTooltip(null);
                        setStyle(Panels.TABLE_CELL_STYLE_EMPTY);
                        setGraphic(null);
                    } else {
                        setText(item.toString());
                        setTooltip(new Tooltip(item.toString()));
                        setAlignment(toAlign ? Pos.CENTER : Pos.CENTER_LEFT);
                        if (!"-->".equals(item.toString())) {
                            setStyle(Panels.TABLE_CELL_STYLE_FILLED);
                        } else {
                            setStyle(Panels.TABLE_CELL_STYLE_EMPTY);
                        }
                    }
                }
            });

            col.setCellValueFactory(this::returnValue);
            col.setId(String.valueOf(i));
            getColumns().add(col);
        }
        getColumns().add(new TableColumn<>());
    }

    public abstract ObservableValue<T> returnValue(CellDataFeatures<S, T> p);
}

package gui.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class utils {
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Integer tryParseToInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static <T> void formatTableColumnLocalDate(TableColumn<T, LocalDate> tableColumn, String format) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	    
	    tableColumn.setCellFactory(column -> {
	        TableCell<T, LocalDate> cell = new TableCell<T, LocalDate>() {
	            @Override
	            protected void updateItem(LocalDate item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty || item == null) {
	                    setText(null);
	                } else {
	                    setText(formatter.format(item));
	                }
	            }
	        };
	        return cell;
	    });
	}
	
	public static <T> void formatTableColumnTimestamp(TableColumn<T, Timestamp> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Timestamp> cell = new TableCell<T, Timestamp>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				@Override
				protected void updateItem(Timestamp item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}
	
	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}
	
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText("R$ " + String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}
	
	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {
			
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
			
			{
				datePicker.setPromptText(format.toLowerCase());
			}
			
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}
			
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}
	
	public static void applyCpfMask(TextField textField) {
	    textField.textProperty().addListener((obs, oldValue, newValue) -> {
	        // Verifica se o newValue é nulo
	        if (newValue != null) {
	            // Remove caracteres não numéricos
	            String numericValue = newValue.replaceAll("[^\\d]", "");

	            // Limita a entrada a 11 dígitos
	            if (numericValue.length() > 11) {
	                numericValue = numericValue.substring(0, 11);
	            }

	            // Formata o CPF
	            StringBuilder formattedCpf = new StringBuilder();
	            if (numericValue.length() > 3) {
	                formattedCpf.append(numericValue.substring(0, 3)).append(".");
	                if (numericValue.length() > 6) {
	                    formattedCpf.append(numericValue.substring(3, 6)).append(".");
	                    if (numericValue.length() > 9) {
	                        formattedCpf.append(numericValue.substring(6, 9)).append("-");
	                        formattedCpf.append(numericValue.substring(9));
	                    } else {
	                        formattedCpf.append(numericValue.substring(6));
	                    }
	                } else {
	                    formattedCpf.append(numericValue.substring(3));
	                }
	            } else {
	                formattedCpf.append(numericValue);
	            }

	            // Atualiza o texto no campo
	            textField.setText(formattedCpf.toString());
	            textField.positionCaret(formattedCpf.length()); // Mantém o cursor no final
	        }
	    });
	}


}

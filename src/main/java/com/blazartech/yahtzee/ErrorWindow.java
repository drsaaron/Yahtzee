/** @author Scott Aaron
    @version $Header: ErrorWindow.java,v 1.2 2000/05/31 17:09:36 aar1069 Exp $ */

/* $Log:	ErrorWindow.java,v $
Revision 1.2  2000/05/31  17:09:36  17:09:36  aar1069 (Aaron  Scott)
Set the size of the dialog box.  Place the label text in the center of 
the label widget, and the label widget in the center of the dialog.

****************************************************************************/

package com.blazartech.yahtzee;

import java.awt.*;
import java.awt.event.*;

/** A simple error window. */
public class ErrorWindow extends Dialog {
    public ErrorWindow(String message) {
        super(new Frame(), "Error", true);
        setLayout(new BorderLayout());

        // Show the message.
        Label text = new Label(message, Label.CENTER);
        add(text, BorderLayout.CENTER);

        // Add an "OK" button.
        Button ok = new Button("OK");
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        add(ok, BorderLayout.SOUTH);

	// Set the size.
	setSize(300,125);

        // Show the window.
        show();
    }
};

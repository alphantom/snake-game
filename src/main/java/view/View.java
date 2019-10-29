package view;

import util.draw.DrawObserver;

public interface View {
     DrawObserver getDrawPanel();
     StatusPanel getStatusPanel();
     void launch();
}

package view;

import util.draw.DrawObserver;

public interface View {
     DrawObserver getDrawPanel();
     void launch();
}

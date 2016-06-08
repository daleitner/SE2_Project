package interfaces;

import screens.GameScreen;

public interface ActionResolver {
    public void showToast(CharSequence text);
    public void shiftGameScreen(GameScreen gs);
}

import aima.search.framework.GoalTest;

public class CarSharingGoalTest implements GoalTest{
    public boolean isGoalState(Object aState) {
    CarSharingBoard board=(CarSharingBoard)aState;
    return(board.isGoalState());
  }
}

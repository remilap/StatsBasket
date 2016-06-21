/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

/**
 * @author lapoint1
 *
 */
public interface CommonScoringDisplayable {

	/**
	 * runningGame: the game is running
	 */
	public void runningGame();

	/**
	 * stoppingGame: the game is stopped
	 */
	public void stoppingGame();

	/**
	 * pausingGame: the game is pausing
	 */
	public void pausingGame();

	/**
	 * setClubName
	 * @param team: 0 for team A, 1 for team B
	 * @param name: new name for the team
	 */
	public void setClubName(int team, String name);

	/**
	 * setScore
	 * @param team: 0 for team A, 1 for team B
	 * @param score: absolute score
	 */
	public void setScore(int team, int score);

	/**
	 * updateScore
	 * @param team: 0 for team A, 1 for team B
	 * @param diff: relative score (positive for adding, negative for subtracting)
	 */
	public void updateScore(int team, int diff);

	/**
	 * setPeriodNumber
	 * @param period: the number of the period
	 */
	public void setPeriodNumber(int period);

	/**
	 * setPlayTime
	 * @param hour: number of hours or -1 for no display
	 * @param minute: number of minutes or -1 for no display
	 * @param second: number of seconds
	 * @param tenths: number of tenths of seconds or -1 for no display
	 */
	public void setPlayTime(int hour, int minute, int second, int tenths);
	public void setPlayTime(int minute, int second);

	/**
	 * setPauseNumber
	 * @param team: 0 for team A, 1 for team B
	 * @param n: number of pauses already taken
	 */
	public void setPauseNumber(int team, int n);

}

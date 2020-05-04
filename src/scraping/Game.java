package scraping;

import java.util.ArrayList;

import javax.swing.text.AbstractDocument.LeafElement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Game {

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public Element extractGame(Document documento) {
		ArrayList<Element> teams = this.extractData(documento);
		Element teamAway = teams.get(0);
		Element teamHome = teams.get(1);

//		teamAway.appendChild(this.extracLineScore(documento, teamAway.attr("url")));
//		teamHome.appendChild(this.extracLineScore(documento, teamHome.attr("url")));

//		teamAway.appendChild(this.extractBasicBoxScoreStats(documento, teamAway.attr("id")));
//		teamHome.appendChild(this.extractBasicBoxScoreStats(documento, teamHome.attr("id")));
		
		Element game = documento.createElement("game").appendChild(teamAway);
		game.appendChild(teamHome);
		
//		game.appendChild(this.extractPlayerInactive(documento));
//		game.appendChild(this.extractOfficials(documento));
//		game.appendChild(this.extractAttendance(documento));
//		game.appendChild(this.extractTimeOfGame(documento));
		
		game.attr("asistencia", this.extractAsistencia(documento));
		
		return game;
	}
	private String extractAsistencia(Document documento) {
		Element attendanceElement = documento.select("div>div>div>div.scorebox_meta>div>small").first();
		return attendanceElement.text().replaceAll(",", "");
	}
	public Element extractTimeOfGame(Document documento) {
		Element TimeOfGame = documento.createElement("timeofgame");
		Element TimeOfGameElement = documento.select("div  > strong:contains(Time Of Game)").first().parents().first();
		String strTimeOfGameText = TimeOfGameElement.html();
		String [] strTimeOfGameArray = strTimeOfGameText.split("</strong>");
		String strTimeOfGame = strTimeOfGameArray[1].trim();
		String [] arrayTimeOfGame = strTimeOfGame.split(":");
		Integer timeGame = Integer.valueOf(arrayTimeOfGame[0])*60 + Integer.valueOf(arrayTimeOfGame[1]);  
		return TimeOfGame.attr("minute", String.valueOf(timeGame));
	}
	public Element extractAttendance(Document documento) {
		Element attendance = documento.createElement("attendance");
		Element attendanceElement = documento.select("div  > strong:contains(Attendance)").first().parents().first();
		String strAttendanceText = attendanceElement.html();
		String [] strAttendanceArray = strAttendanceText.split("</strong>");
		String strAttendance = strAttendanceArray[1].replaceAll(",", "");
		return attendance.attr("person", strAttendance);
	}
	public Element extractOfficials(Document documento) {
		Element officials = documento.createElement("referees");
		Elements officialsElements = documento.select("div  > strong:contains(Officials)").first().parents().first().select("a");
		for (Element element : officialsElements) {
			Element official = documento.createElement("referee");
			official.attr("url", element.attr("href"));
			official.attr("name", element.text());
			officials.appendChild(official);
		}
		return officials;
	}
	public Element extractPlayerInactive(Document documento) {
		Element inactive = documento.createElement("inactive");
		Elements inactivePlayerElements = documento.select("div  > strong:contains(Inactive)").first().parents().first().select("a");
		for (Element element : inactivePlayerElements) {
			Element playerInactive = documento.createElement("player");
			playerInactive.attr("url", element.attr("href"));
			playerInactive.attr("name", element.text());
			inactive.appendChild(playerInactive);
		}
		return inactive;
	}
	
	public Element extractBasicBoxScoreStats(Document documento, String id) {

		Element basicBoxScoreStats = documento.createElement("basicBoxScoreStats");
		Element basicBoxScoreStatsPlayer = documento.createElement("player");

		Elements basicScoreTrElements = documento.select("table#box-" + id + "-game-basic > tbody > tr");
		Elements advancedScoreTrElements = documento.select("table#box-" + id + "-game-advanced > tbody > tr");

		int iTr = 1;
		for (Element element : basicScoreTrElements) {
			if (iTr != 6) {

				basicBoxScoreStatsPlayer = documento.createElement("player");

				Elements basicScoreTh = element.select("th");
				basicBoxScoreStatsPlayer.attr("name", basicScoreTh.get(0).text());
				basicBoxScoreStatsPlayer.attr("id", basicScoreTh.get(0).attr("data-append-csv"));
				basicBoxScoreStatsPlayer.attr("url", basicScoreTh.get(0).select("a").get(0).attr("href"));
				Elements basicScoreTd = element.select("td");
				int iTd = 1;
				String attrName = "";
				for (Element element2 : basicScoreTd) {
					attrName = "";
					switch (iTd) {
					case 1:
						attrName = "MinutesPlayed";
						break;
					case 2:
						attrName = "FieldGoals";
						break;
					case 3:
						attrName = "FieldGoalAttempts";
						break;
					case 4:
						attrName = "FieldGoalPercentage";
						break;
					case 5:
						attrName = "3PointFieldGoals";
						break;
					case 6:
						attrName = "3PointFieldGoalAttempts";
						break;
					case 7:
						attrName = "3PointFieldGoalPercentage";
						break;
					case 8:
						attrName = "FreeThrows";
						break;
					case 9:
						attrName = "FreeThrowAttempts";
						break;
					case 10:
						attrName = "FreeThrowPercentage";
						break;
					case 11:
						attrName = "OffensiveRebounds";
						break;
					case 12:
						attrName = "DefensiveRebounds";
						break;
					case 13:
						attrName = "TotalRebounds";
						break;
					case 14:
						attrName = "Assists";
						break;
					case 15:
						attrName = "Steals";
						break;
					case 16:
						attrName = "Blocks";
						break;
					case 17:
						attrName = "Turnovers";
						break;
					case 18:
						attrName = "PersonalFouls";
						break;
					case 19:
						attrName = "Points";
						break;
					case 20:
						attrName = "PlusMinus";
						break;
					default:
						attrName = "Others";
						break;
					}
					basicBoxScoreStatsPlayer.attr(attrName, element2.text());
					iTd++;
				}
				// advancedBoxScore
				Element advancedBoxScoreElement = advancedScoreTrElements.get(iTr - 1);
				Elements advancedBoxScoreElementTd = advancedBoxScoreElement.select("td");
				int iTdAdvance = 1;
				for (Element element3 : advancedBoxScoreElementTd) {
					attrName = "";
					if (iTdAdvance > 1) {
						switch (iTdAdvance) {
						case 2:
							attrName = "TrueShootingPercentage";
							break;
						case 3:
							attrName = "EffectiveFieldGoalPercentage";
							break;
						case 4:
							attrName = "3PointAttemptRate";
							break;
						case 5:
							attrName = "FreeThrowAttemptRate";
							break;
						case 6:
							attrName = "OffensiveReboundPercentage";
							break;
						case 7:
							attrName = "DefensiveReboundPercentage";
							break;
						case 8:
							attrName = "TotalReboundPercentage";
							break;
						case 9:
							attrName = "AssistPercentage";
							break;
						case 10:
							attrName = "StealPercentage";
							break;
						case 11:
							attrName = "BlockPercentage";
							break;
						case 12:
							attrName = "TurnoverPercentage";
							break;
						case 13:
							attrName = "UsagePercentage";
							break;
						case 14:
							attrName = "OffensiveRating";
							break;
						case 15:
							attrName = "DefensiveRating";
							break;
						case 16:
							attrName = "BPM";
							break;
						default:
							attrName = "Others";
							break;
						}
						basicBoxScoreStatsPlayer.attr(attrName, element3.text());
						
					}
					iTdAdvance++;
				}
			}

			iTr++;
			basicBoxScoreStats.appendChild(basicBoxScoreStatsPlayer);
		}

		return basicBoxScoreStats;
	}

	public ArrayList<Element> extractData(Document documento) {
		Elements scoreboxElements = documento.select("div > div > div.scorebox > div > div[itemprop]");

		Elements scoreElements = documento.select("div.scorebox > div > div.scores > div.score");

		Elements scoreElementsDiv = documento.select("div.scorebox > div > div");
		
		Elements scoreboxElementsScore = documento.select("div > div > div.scorebox > div > div.scores > div.score");
		
		/* TEAM AWAY */
		Element teamAwayLogo = scoreboxElements.get(1).select("strong > a").first();
		Element teamAwayPhoto = scoreboxElements.get(1).select("div > img").first();
		Element teamAway = documento.createElement("team").attr("name", teamAwayLogo.text());
		teamAway.attr("url", teamAwayLogo.attr("href"));
		teamAway.attr("logo", teamAwayPhoto.attr("src"));
		teamAway.attr("goal", scoreboxElementsScore.get(1).text());
		teamAway.attr("idteam", this.idTeam(teamAwayPhoto.attr("src")));
		
		teamAway.appendChild(extractPlayer(documento, teamAway.attr("idteam")));
		teamAway.appendChild(extractPlayerPortero(documento, teamAway.attr("idteam")));

		/* TEAM HOME */
		Element teamHomeLogo = scoreboxElements.get(0).select("strong > a").first();
		Element teamHomePhoto = scoreboxElements.get(0).select("div > img").first();
		Element teamHome = documento.createElement("team").attr("name", teamHomeLogo.text());
		teamHome.attr("url", teamHomeLogo.attr("href"));
		teamHome.attr("logo", teamHomePhoto.attr("src"));
		teamHome.attr("goal", scoreboxElementsScore.get(0).text());
		teamHome.attr("idteam", this.idTeam(teamHomePhoto.attr("src")));
		
		teamHome.appendChild(extractPlayer(documento, teamHome.attr("idteam")));
		teamHome.appendChild(extractPlayerPortero(documento, teamHome.attr("idteam")));

//		teamHome.appendChild(extracLineScore(documento, urlTeamHome));

//		Element game =  documento.createElement("game").appendChild(teamAway);
//		game.appendChild(teamHome);

		ArrayList<Element> teams = new ArrayList<Element>();
		teams.add(teamAway);
		teams.add(teamHome);
		return teams;
	}

	private String splitStringBySeparator(String url, String stringSeparator, Integer pos) {
		String[] fragmentos = url.split(stringSeparator);
		return fragmentos[pos];
	}

	public Element extracLineScore(Document documento, String teamUrl) {
		Element lineScoreElement = null;
//		String cadena = documento.toString();
//		Elements prueba = documento.select("comment");
		Elements prueba = documento.select("div.content_grid > div > div.table_wrapper");
		String lineScore = prueba.get(0).toString().replaceAll("<!--", "").replaceAll("-->", "");
		Document doc = Jsoup.parse(lineScore);
		Elements trArray = doc.select("table > tbody > tr");

		for (Element element : trArray) {
			Elements urlElements = element.select("td");
			if (urlElements.size() > 0) {
				String url = urlElements.get(0).select("a").attr("href").toString();
				if (url.equals(teamUrl)) {

					lineScoreElement = documento.createElement("linescore");
					int contador = 0;
					for (Element element2 : urlElements) {
						if ((contador > 0) && (contador < urlElements.size() - 1)) {
							Element scoreElement = documento.createElement("score")
									.attr("time", String.valueOf(contador)).attr("points", element2.text());
							lineScoreElement.appendChild(scoreElement);
						}
						contador++;
//						System.out.println(element.toString());
					}
				}
			}
		}
//		Elements tdArray = trArray.get(2).select("td");
//		> div.overthrow.table_container > div.overthrow.table_container> table");
		return lineScoreElement;
	}
	
	private Element extractPlayer(Document documento, String idTeam) {
		Element players = null;
		players = documento.createElement("players");
		
		Elements playersListElement = documento.select("table#stats_"+ idTeam +"_summary>tbody>tr");
		for (Element element : playersListElement) {
			Element player = documento.createElement("player");
//			String strPlayer =	element.select("th>a").first().html();
//			System.out.println(strPlayer);
			player.attr("player_name", 					element.select("th>a").first().text().trim());
			player.attr("player_url", 					element.select("th>a").first().attr("href").trim());
			player.attr("nationality", 					element.select("td[data-stat=nationality]>a>span").first().text().split(" ")[1]);
			player.attr("position", 					element.select("td[data-stat=position]").first().text());
			player.attr("minutes", 						element.select("td[data-stat=minutes]").first().text());
			player.attr("goals", 						element.select("td[data-stat=goals]").first().text());
			player.attr("assists", 						element.select("td[data-stat=assists]").first().text());
			player.attr("pens_made", 					element.select("td[data-stat=pens_made]").first().text());
			player.attr("pens_att", 					element.select("td[data-stat=pens_att]").first().text());
			player.attr("shots_total", 					element.select("td[data-stat=shots_total]").first().text());
			player.attr("shots_on_target", 				element.select("td[data-stat=shots_on_target]").first().text());
			player.attr("cards_yellow", 				element.select("td[data-stat=cards_yellow]").first().text());
			player.attr("cards_red", 					element.select("td[data-stat=cards_red]").first().text());
			player.attr("touches", 						element.select("td[data-stat=touches]").first().text());
			player.attr("pressures", 					element.select("td[data-stat=pressures]").first().text());
			player.attr("tackles", 						element.select("td[data-stat=tackles]").first().text());
			player.attr("interceptions", 				element.select("td[data-stat=interceptions]").first().text());
			player.attr("blocks", 						element.select("td[data-stat=blocks]").first().text());
			player.attr("xg", 							element.select("td[data-stat=xg]").first().text());
			player.attr("npxg", 						element.select("td[data-stat=npxg]").first().text());
			player.attr("xa", 							element.select("td[data-stat=xa]").first().text());
			player.attr("sca", 							element.select("td[data-stat=sca]").first().text());
			player.attr("gca", 							element.select("td[data-stat=gca]").first().text());
			player.attr("passes_completed", 			element.select("td[data-stat=passes_completed]").first().text());
			player.attr("passes", 						element.select("td[data-stat=passes]").first().text());
			player.attr("passes_pct", 					element.select("td[data-stat=passes_pct]").first().text());
			player.attr("passes_progressive_distance", 	element.select("td[data-stat=passes_progressive_distance]").first().text());
			player.attr("carries", 						element.select("td[data-stat=carries]").first().text());
			player.attr("carry_progressive_distance", 	element.select("td[data-stat=carry_progressive_distance]").first().text());
			player.attr("dribbles_completed", 			element.select("td[data-stat=dribbles_completed]").first().text());
			player.attr("dribbles", 					element.select("td[data-stat=dribbles]").first().text());
			players.appendChild(player);
		}
		
		return players;
	}
	private Element extractPlayerPortero(Document documento, String idTeam) {
		Element players = null;
		players = documento.createElement("goalkeepers");
		
		Elements playersListElement = documento.select("table#keeper_stats_"+ idTeam +">tbody>tr");
		for (Element element : playersListElement) {
			Element player = documento.createElement("player");
			player.attr("player_name", 					element.select("th>a").first().text().trim());
			player.attr("player_url", 					element.select("th>a").first().attr("href").trim());
			player.attr("nationality", 					element.select("td[data-stat=nationality]>a>span").first().text().split(" ")[1]);
			player.attr("position", 					"GK");
			player.attr("shots_on_target_against", 		element.select("td[data-stat=shots_on_target_against]").first().text());
			player.attr("goals_against", 				element.select("td[data-stat=goals_against]").first().text());
			player.attr("saves", 						element.select("td[data-stat=saves]").first().text());
			player.attr("save_perc", 					element.select("td[data-stat=save_perc]").first().text());
			player.attr("psxg_gk", 						element.select("td[data-stat=psxg_gk]").first().text());
			player.attr("passes_completed_launched_gk", element.select("td[data-stat=passes_completed_launched_gk]").first().text());
			player.attr("passes_launched_gk", 			element.select("td[data-stat=passes_launched_gk]").first().text());
			player.attr("passes_pct_launched_gk", 		element.select("td[data-stat=passes_pct_launched_gk]").first().text());
			player.attr("passes_gk", 					element.select("td[data-stat=passes_gk]").first().text());
			player.attr("passes_throws_gk", 			element.select("td[data-stat=passes_throws_gk]").first().text());
			player.attr("pct_passes_launched_gk", 		element.select("td[data-stat=pct_passes_launched_gk]").first().text());
			player.attr("passes_length_avg_gk", 		element.select("td[data-stat=passes_length_avg_gk]").first().text());
			player.attr("goal_kicks", 					element.select("td[data-stat=goal_kicks]").first().text());
			player.attr("pct_goal_kicks_launched", 		element.select("td[data-stat=pct_goal_kicks_launched]").first().text());
			player.attr("goal_kick_length_avg", 		element.select("td[data-stat=goal_kick_length_avg]").first().text());
			player.attr("crosses_gk", 					element.select("td[data-stat=crosses_gk]").first().text());
			player.attr("crosses_stopped_gk", 			element.select("td[data-stat=crosses_stopped_gk]").first().text());
			player.attr("crosses_stopped_pct_gk", 		element.select("td[data-stat=crosses_stopped_pct_gk]").first().text());
			player.attr("def_actions_outside_pen_area_gk",	element.select("td[data-stat=def_actions_outside_pen_area_gk]").first().text());
			player.attr("avg_distance_def_actions_gk", 	element.select("td[data-stat=avg_distance_def_actions_gk]").first().text());
			players.appendChild(player);
		}
		
		return players;
	}
	private String idTeam(String strSrcLogo) {
		String [] splitSrcLogo = strSrcLogo.split("/");
		return splitSrcLogo[splitSrcLogo.length - 1].replaceAll(".png", "");
	}

}

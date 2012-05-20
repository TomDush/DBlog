package fr.dush.test.dblog.dao.model;

import java.util.List;

import fr.dush.test.dblog.dao.IGenericDAO;
import fr.dush.test.dblog.dto.model.Score;
import fr.dush.test.dblog.dto.model.Ticket;

public interface IScoreDAO extends IGenericDAO<Score> {

	double calculScore(Ticket ticket);

	List<Score> findByTicket(Ticket ticket);
}

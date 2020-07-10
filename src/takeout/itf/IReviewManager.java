package takeout.itf;

import takeout.util.BaseException;

import java.util.List;

import takeout.model.*;
public interface IReviewManager {
	public List<Review> loadAllBReviews(String businessid)throws BaseException;
	
	public List<Review> loadAllUReviews(String userid)throws BaseException;

	public void addReview(Review review)throws BaseException;
}

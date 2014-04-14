package net.balmeyer.qno.news;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestQnoNews {

	@Test
	public void cultures() {
		QnoNews qn = new QnoNews();

		Culture[] cultures = qn.getCultures();

		assertNotNull(cultures);
		assertTrue(cultures.length > 0);

		for (Culture c : cultures) {
			assertNotNull(c.country_code);
			assertNotNull(c.culture_code);
			assertNotNull(c.display_culture_name);
			assertNotNull(c.english_culture_name);
			assertNotNull(c.language_code);
		}
	}

	@Test
	public void categories() {
		QnoNews qn = new QnoNews();

		Category[] cats = qn.getCategories();

		assertNotNull(cats);

		int countSub = 0;

		for (Category c : cats) {
			assertTrue(c.category_id > 0);
			assertNotNull(c.display_category_name);
			assertNotNull(c.english_category_name);
			assertNotNull(c.url_category_name);

			if (countSub++ < 3) {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					Subcategory[] subs = qn.getSubcategories(c);

					for (Subcategory s : subs) {
						testSub(s);
					}
				} catch (Exception ex) {
					fail("Erreur category " + c + " - " + ex.getMessage());

				}
			}
		}

		Subcategory[] subs = qn.getSubcategories();
		for (Subcategory s : subs) {
			testSub(s);
		}
	}

	@Test
	public void testArticle(){
		QnoNews qn = new QnoNews();

		Category[] cats = qn.getCategories();

		assertNotNull(cats);
		
		Category c = cats[0];
		
		Articles articles = qn.getArticles(c);
		
		assertNotNull(articles);
		
		assertTrue(articles.articles.length > 0);
		
		for(Article a : articles.articles){
			//assertNotNull(a.author);
			assertNotNull(a.publish_date);
			assertNotNull(a.source);
			assertNotNull(a.source_url);
			assertNotNull(a.summary);
			assertNotNull(a.url);
		}
		
		
	}
	
	private void testSub(Subcategory sub) {
		assertNotNull(sub);
		assertNotNull(sub.display_subcategory_name);
		assertNotNull(sub.english_subcategory_name);
		assertNotNull(sub.url_subcategory_name);
		assertTrue(sub.category_id > 0);
		assertTrue(sub.subcategory_id > 0);
	}

}

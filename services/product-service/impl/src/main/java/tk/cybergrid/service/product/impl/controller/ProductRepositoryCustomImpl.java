package tk.cybergrid.service.product.impl.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import tk.cybergrid.service.product.impl.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Finds products matching given name, minimum/maximum price and description. Name and description matching is
     * case-insensitive and non-exact (given name and description can be a substring of full product name and
     * description). Null parameters will be ignored and will not be included in the query.
     */
    @Override
    public List<Product> findByNamePriceAndDescription(final String name,
                                                       final BigDecimal minPrice,
                                                       final BigDecimal maxPrice,
                                                       final String description) {
        // prepare query base
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        final Root<Product> product = query.from(Product.class);
        final List<Predicate> predicates = new ArrayList<>();

        // add name predicate
        if (StringUtils.isNotBlank(name)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(product.get(Product.COLUMN_NAME_NAME)),
                    "%" + name.toLowerCase() + "%"));
        }

        // add price predicates
        if (minPrice != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(product.get(Product.COLUMN_NAME_PRICE), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(product.get(Product.COLUMN_NAME_PRICE), maxPrice));
        }

        // add description predicates
        if (StringUtils.isNotBlank(description)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(product.get(Product.COLUMN_NAME_DESCRIPTION)),
                    "%" + description.toLowerCase() + "%"));
        }

        query.select(product)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        return entityManager.createQuery(query).getResultList();
    }

}

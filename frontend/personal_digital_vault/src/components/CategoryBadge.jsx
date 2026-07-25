function CategoryBadge({ category }) {
  return (
    <span className="category-badge">
      {category || "General"}
    </span>
  );
}

export default CategoryBadge;
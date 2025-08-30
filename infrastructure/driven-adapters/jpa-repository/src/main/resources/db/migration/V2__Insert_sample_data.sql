-- Sample data for testing
-- V2__Insert_sample_data.sql

-- Insert sample franchises
INSERT INTO franchises (name) VALUES 
('McDonald''s'),
('Burger King'),
('KFC');

-- Insert sample branches
INSERT INTO branches (name, franchise_id) VALUES 
('McDonald''s Centro', 1),
('McDonald''s Norte', 1),
('Burger King Plaza', 2),
('Burger King Mall', 2),
('KFC Express', 3);

-- Insert sample products
INSERT INTO products (name, stock, branch_id) VALUES 
-- McDonald's Centro
('Big Mac', 50, 1),
('French Fries', 100, 1),
('Coca Cola', 80, 1),
-- McDonald's Norte  
('Big Mac', 75, 2),
('McNuggets', 60, 2),
('Apple Pie', 30, 2),
-- Burger King Plaza
('Whopper', 45, 3),
('Onion Rings', 90, 3),
('Pepsi', 70, 3),
-- Burger King Mall
('Whopper', 85, 4),
('Chicken Royale', 40, 4),
-- KFC Express
('Original Recipe', 120, 5),
('Hot Wings', 95, 5),
('Coleslaw', 55, 5);

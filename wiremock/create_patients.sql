
-- Insert first 5 patients (half of the total)
-- Note: Using status_id 1 for ACTIVE, 0 for INACTIVE
INSERT INTO patient_profile (first_name, last_name, old_client_guid, status_id) VALUES
('John', 'Doe', '01588E84-D45A-EB98-F47F-716073A4F1EF', 210),
('Jane', 'Smith', '02588E84-D45A-EB98-F47F-716073A4F1EF', 0),
('Michael', 'Johnson', '03588E84-D45A-EB98-F47F-716073A4F1EF', 230),
('Sarah', 'Williams', '04588E84-D45A-EB98-F47F-716073A4F1EF', 210),
('David', 'Brown', '05588E84-D45A-EB98-F47F-716073A4F1EF', 0);

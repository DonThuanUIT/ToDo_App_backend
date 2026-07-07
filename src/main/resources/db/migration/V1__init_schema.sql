CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workspaces (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            name VARCHAR(255) NOT NULL,
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
                          name VARCHAR(255) NOT NULL,
                          is_default BOOLEAN DEFAULT FALSE,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE location_books (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                name VARCHAR(255) NOT NULL,
                                address TEXT,
                                latitude DOUBLE PRECISION,
                                longitude DOUBLE PRECISION,
                                geofence_radius INT DEFAULT 200,
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tasks (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       project_id UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
                       location_id UUID REFERENCES location_books(id) ON DELETE SET NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       due_date DATE,
                       priority INT DEFAULT 4,
                       status VARCHAR(50) DEFAULT 'TODO',
                       completed_at TIMESTAMP WITH TIME ZONE,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tasks_project_id ON tasks(project_id);
CREATE INDEX idx_tasks_due_date ON tasks(due_date);

CREATE TABLE habits (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        name VARCHAR(255) NOT NULL,
                        schedule_config VARCHAR(255),
                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE habit_logs (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            habit_id UUID NOT NULL REFERENCES habits(id) ON DELETE CASCADE,
                            log_date DATE NOT NULL,
                            is_completed BOOLEAN DEFAULT FALSE,
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_habit_logs_log_date ON habit_logs(log_date);
CREATE INDEX idx_habit_logs_habit_id ON habit_logs(habit_id);

CREATE TABLE user_daoists (
                              user_id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                              current_points INT DEFAULT 0,
                              daily_goal INT DEFAULT 5,
                              weekly_goal INT DEFAULT 25,
                              daily_streak INT DEFAULT 0,
                              weekly_streak INT DEFAULT 0,
                              last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE daoist_logs (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             points_changed INT NOT NULL,
                             action_type VARCHAR(100) NOT NULL,
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
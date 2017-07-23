CREATE TABLE studios (
  id        VARCHAR(36) PRIMARY KEY,
  name      VARCHAR(30),
  payment   DECIMAL
);

CREATE TABLE episodes (

  id        VARCHAR(36) PRIMARY KEY,
  name      VARCHAR(30),
  studioId  VARCHAR(36)
);
ALTER TABLE episodes ADD FOREIGN KEY (studioId) REFERENCES studios(id);

CREATE TABLE viewings (

  episodeId VARCHAR(36),
  userId    VARCHAR(30)
);
ALTER TABLE viewings ADD FOREIGN KEY (episodeId) REFERENCES episodes(id);

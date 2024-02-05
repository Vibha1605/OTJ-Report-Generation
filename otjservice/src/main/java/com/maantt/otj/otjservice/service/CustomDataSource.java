package com.maantt.otj.otjservice.service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.model.SkillCluster;



import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CustomDataSource implements JRDataSource {

    private AssessmentReport masterDetails;
    private StringBuilder demonstrateOutput = new StringBuilder();
    private StringBuilder improveOutput = new StringBuilder();
    private StringBuilder focusOutput = new StringBuilder();
    // private StringBuilder allFeaturesOutput = new StringBuilder();
    // private Integer alltopicwiseScoreOutput = new StringBuilder();
    private boolean hasProcessedData = false;
    private List<FeatureScore> featureScores = new CopyOnWriteArrayList<>();


    private static final Logger log = LoggerFactory.getLogger(CustomDataSource.class);

    public CustomDataSource(AssessmentReport masterDetails) {
        if (masterDetails == null) {
            throw new IllegalArgumentException("AssessmentReport cannot be null");
        } else {
            log.info("Generating Report Wait!!!.....");
        }
        this.masterDetails = masterDetails; // Populate output strings during initialization
    }

    @Override
    public boolean next() throws JRException {
        // Check if it's the first call to next()
        if (!hasProcessedData) {
            hasProcessedData = true;
            populateOutputStrings();
            return true; // Return true to indicate that there is data
        }
        return false;
    }
    
    public static class FeatureScore {
        private String features;
        private int topicwiseScore;

        public FeatureScore(String features, int topicwiseScore) {
            this.features = features;
            this.topicwiseScore = topicwiseScore;
        }

        public String getFeatures() {
        	log.info("{}",features);
            return features;
        }

        public int getTopicwiseScore() {
        	log.info("{}",topicwiseScore);
            return topicwiseScore;
        }
    }


    public void populateOutputStrings() {
        log.info("Populating output strings for master: {}", masterDetails);
        List<SkillCluster> skillClusters = masterDetails.getSkillClusters();
        if (skillClusters != null && !skillClusters.isEmpty()) {
            for (SkillCluster skillCluster : skillClusters) {
                int topicwiseScore = skillCluster.getTopicwiseScore();
                String features = skillCluster.getFeatures();

                String bullet = "*"; // Define your bullet point character or string

                if (topicwiseScore > 80) {
                    demonstrateOutput.append("\t").append(bullet).append(" ").append(features).append("\n");
                } else if (topicwiseScore <= 80 && topicwiseScore > 50) {
                    improveOutput.append("\t").append(bullet).append(" ").append(features).append("\n");
                } else if (topicwiseScore <= 50) {
                    focusOutput.append("\t").append(bullet).append(" ").append(features).append("\n");
                }

                synchronized (featureScores) {
                	log.info("{}",new FeatureScore(features, topicwiseScore));
                    featureScores.add(new FeatureScore(features, topicwiseScore));
                    log.info("{}",featureScores);
                }
            }
        }
    }

    public String getDemonstrateOutput() {
        return demonstrateOutput.toString();
    }

    public String getImproveOutput() {
        return improveOutput.toString();
    }

    public String getFocusOutput() {
        return focusOutput.toString();
    }

    // public String getAllFeaturesOutput() {
    //     return allFeaturesOutput.toString();
    // }

    // public Integer getAllTopicwiseScoreOutput() {
    //     return alltopicwiseScoreOutput;
    // }

    public List<FeatureScore> getFeatureScores() {
        return featureScores;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (masterDetails == null) {
            return null; // Handle null case gracefully
        }

        String fieldName = jrField.getName();
        log.info("{}", fieldName);

        switch (fieldName) {
            case "User_id":
                log.info("User id:{}", masterDetails.getId());
                return masterDetails.getId();
            case "Associate_id":
                log.info("User id:{}", masterDetails.getAssociateId());
                return masterDetails.getAssociateId();
            case "Associate_email":
                return masterDetails.getAssociateEmail();
            case "Associate_name":
                return masterDetails.getAssociateName();
            case "Otj_name":
                return masterDetails.getOtjName();
            case "Otj_code":
                return masterDetails.getOtjCode();
            case "Status":
                return masterDetails.getStatus();
            case "Score":
                return masterDetails.getScore();
            default:
                log.warn("Unknown field: {}", fieldName);
                return null;
        }
    }
}


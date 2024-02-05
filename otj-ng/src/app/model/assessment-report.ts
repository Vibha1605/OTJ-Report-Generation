import { SkillCluster } from "./skill-cluster";


export interface AssessmentReport {
    id: number,
    associateId: number,
    associateName: string,
    associateEmail: string,
    otjName: string,
    otjCode: string,
    otjReceivedDate: Date,
    plannedDeliveryDate: Date,
    actualDeliveryDate: Date,
    score: number,
    status: string,
    skillClusters: SkillCluster[]
}
